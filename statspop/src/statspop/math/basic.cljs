(ns statspop.math.basic
  "basic math and stats functions"
  (:require [goog.math :as math]))

(defn stdev
  "standard deviation. vec of nums -> num"
  [nums]
  (apply math/standardDeviation nums))

(defn mean
  "mean. vec of nums -> num"
  [nums]
  (let [sum (apply + nums)
        len (count nums)]
    (/ sum len)))

(defn elementwise-vector-multiply
  "the name says it all: multiply the corresponding elements of two vectors together"
  [vec1 vec2]
  (map #(apply * %) (mapv vector vec1 vec2)))

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

;; (defn correlation-matrix
;;   "not actually a matrix, this will just return a map of correlations, because correlation matrices are an unnecessarily redundant and mentally exhausting presentation of information.

;;   vector of numeric vectors --> map of string-num parings

;;   assumes column-wise data input.

;;   TODO"
;;   [])

;; experimenting with for loop.  this produces uniques. 

;; (def test1 [1 2 3])
;; (vec (set (for [i test1, j test1] (str (max i j) (min i j) "=" (+ i j)))))
;; good news is that max and min work with strings too.

;; actually though it makes more sense to just use a reduce or a recursive function. or a reduce nested in a recursive function.
;;
;; first iteration: map correlation with col1 across rest of matrix (using first and rest). second iteration: recur on the rest.
;;

(defn labelled-correlation
  [x y]
  (str x "<-->" y (corr x y)))

(defn correlation-matrix
  "not actually a matrix, this will just return a map of correlations, because correlation matrices are an unnecessarily redundant and mentally exhausting presentation of information.

   vector of numeric vectors --> map of string-num parings

   assumes column-wise data input."
  [in-matrix]
  (loop [col (first in-matrix)
         cor-with (rest in-matrix)
         acc []]
    (if (> (count cor-with) 1)
      (recur (first cor-with) (rest cor-with) (concat acc (map (partial labelled-correlation col) cor-with)))
      (vec acc))))

;; UNTESTED. 
