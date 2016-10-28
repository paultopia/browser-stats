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

