(ns statspop.stats-test
  (:require  [devcards.core :as devcards]
             [reagent.core :as r]
             [cljs.test :as t :refer [report] :include-macros true]
             [statspop.math.regression :as reg])
  (:require-macros [devcards.core :as dc :refer [defcard deftest defcard-rg]]
                   [cljs.test :refer [is testing]]))

(deftest test-for-devcards-true (is (= 1 (dec 2))))

(defn *_*
  "approximate equality function to handle floating point inaccuracy. Will test inequality to digits significant digits"
  [form1 form2 digits]
  ("foo"))

(defcard-rg test-card2
  "testing devcards"
  [:div
   [:h1 "This is your second devcard!"]])
