(ns statspop.matrix-test
  (:require  [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
             [reagent.core :as r]
             [statspop.math.matrix :as matrix]
             [cljs.test :as t :refer-macros [is testing]]))

(deftest basic-matrix-transposition
  (is
   (=
    [[1 4] [2 5] [3 6]]
    (-> [[1 2 3] [4 5 6]] matrix/matrixify matrix/transpose matrix/m-to-cljs))))

(deftest matrix-transposition-from-js
  (is
   (=
    [[1 4] [2 5] [3 6]]
    (-> [[1 2 3] [4 5 6]] clj->js matrix/matrixify matrix/transpose matrix/m-to-cljs))))

