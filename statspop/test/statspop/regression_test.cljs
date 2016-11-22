(ns statspop.regression-test
  (:require  [devcards.core :as devcards]
             [reagent.core :as r]
             [cljs.test :as t :refer [report] :include-macros true]
             [statspop.math.regression :as reg])
  (:require-macros [devcards.core :as dc :refer
                    [defcard defcard-doc deftest defcard-rg]]
                   [cljs.test :refer [is testing async]]))

;; reg/cols->rows converts to form for reg/linear-regression
;; should probably fix that crappy api though.

;; test example stolen from Montgomery & Runger, Applied Statistics and Probability for Engineers, 3rd ed., ch. 12


(deftest regression-from-columns
  (let [test-features
        [[2 8 11 10 8 4 2 2 9 8 4 11 12 2 4 4 20 1 10 15 15 16 17 6 5]
         [50 110 120 550 295 200 375 52 100 300 412 400 500 360 205 400 600 585 540 250 290 510 590 100 400]]
        test-labels [9.95 24.45 31.75 35.00 25.02 16.86 14.38 9.60 24.35 27.50 17.08 37.00 41.95 11.66 21.65 17.89 69.00 10.30 34.93 46.59 44.88 54.12 56.63 22.13 21.15]
        {:keys [labels features]} (reg/cols->rows test-labels test-features)]
    (is (= (reg/linear-regression labels features) [[2.2637914344045384] [2.7442696432411866] [0.01252781138560427]]))))


