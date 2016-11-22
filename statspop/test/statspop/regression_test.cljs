(ns statspop.regression-test
  (:require  [devcards.core :as devcards]
             [reagent.core :as r]
             [cljs.test :as t :refer [report] :include-macros true]
             [statspop.math.regression :as reg])
  (:require-macros [devcards.core :as dc :refer
                    [defcard defcard-doc deftest defcard-rg]]
                   [cljs.test :refer [is testing async]]))

;; reg/cols->rows converts to form for reg/linear-regression
;; should actually write the tests.
