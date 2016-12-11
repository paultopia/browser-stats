(ns statspop.math.basic
  "basic math and stats functions"
  (:require [goog.math :as gmath]
            [cljs.spec :as spec]))

(spec/def ::numeric-vector (spec/coll-of number? :kind vector?))

(defn same-length? [v1 v2]
  (= (count v1)
     (count v2)))

(defn stdev
  "standard deviation. vec of nums -> num.  Note, per goog.math api docs, this is sample standard deviation "
  [nums]
  (apply gmath/standardDeviation nums))

(defn mean
  "vec of nums -> num"
  [nums]
  (apply gmath/average nums))

(defn elementwise-vector-multiply
  "the name says it all: multiply the corresponding elements of two vectors together"
  [vec1 vec2]
  (map #(apply * %) (mapv vector vec1 vec2)))

(spec/fdef elementwise-vector-multiply
           :args (spec/and
                  (spec/cat
                   :v1 ::numeric-vector
                   :v2 ::numeric-vector)
                  #(same-length? (:v1 %) (:v2 %))))
;; might be too restrictive, perhaps should accept sequences and such not just vectors

(defn corr
  "sample pearson correlation coefficient of two numeric vectors"
  [x y]
  (let [x-bar (mean x)
        y-bar (mean y)
        sigma-x (stdev x)
        sigma-y (stdev y)
        zed-x (map #(/ (- % x-bar) sigma-x) x)
        zed-y (map #(/ (- % y-bar) sigma-y) y)
        zed-product (elementwise-vector-multiply zed-x zed-y)]
    (/ (apply + zed-product) (- (count x) 1))))

(defn labelled-correlation
  [x y]
  {(str x "<-->" y) (corr x y)})

;; this needs to be changed when I get data structures working right, to use column labels instead of just stringified vectors. 

(defn correlation-matrix
  "not actually a matrix, this will just return a map of correlations, because correlation matrices are an unnecessarily redundant and mentally exhausting presentation of information.

   vector of numeric vectors --> map of string-num parings

   assumes column-wise data input.
  "
  [in-matrix]
  (loop [col (first in-matrix)
         cor-with (rest in-matrix)
         acc []]
    (if (> (count cor-with) 1)
      (recur (first cor-with) (rest cor-with) (concat acc (map (partial labelled-correlation col) cor-with)))
      (apply merge (vec acc)))))

(defn log-transform
"only for columns of data, not rows. pay attention to this.  maybe I need a naming convention for column vs row stuff? Or maybe a separate namespace? statspop.transform.columns, statspop.transform.rows and statspop.transform.datasets?"
  [column]
  (mapv js.Math.log column))

(defn scale
  "center and scale COLUMN, returns column in standard deviation units away from column mean."
  [column]
  (let [m (mean column)
        sd (stdev column)]
    (mapv #(/ (- % m) sd) column)))
