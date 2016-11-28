(ns statspop.math-test
  (:require [statspop.math.basic :as m]
            [statspop.utils-test :refer [*_*]]
            [cljs.test :as t :refer-macros [is testing]]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [reagent.core :as r]
            [cljs.test :as t :refer-macros [is testing]]
            )
  (:require-macros [statspop.tmacros :as mac :refer [rgcard]]))

(rgcard correlation-matrix (m/correlation-matrix [[3.8 3.1 4.0 2.5 3.3] [3.8 3.1 4.0 2.5 3.3] [2.8 2.2 3.5 1.9 2.5]]))

(deftest correlation (is
                      (*_*
                       (m/corr [3.8 3.1 4.0 2.5 3.3] [2.8 2.2 3.5 1.9 2.5])
                       0.9416
                       4)))
;; shamelessly stolen from http://pirate.shu.edu/~wachsmut/Teaching/MATH1101/Relations/correlation.html

(rgcard scaling (m/scale [1 2 3 4 5]))

(rgcard stdev (m/stdev [1 2 3 4 5]))

(deftest scaling-column "scales a column. note: uses sample standard deviation not population standard deviation; this will agree with R's built-in scale() function but disagree with Python sklearn.preprocessing.scale() function."
  (is (= (m/scale [1 2 3 4 5]) [-1.2649110640673518 -0.6324555320336759 0 0.6324555320336759 1.2649110640673518])))
