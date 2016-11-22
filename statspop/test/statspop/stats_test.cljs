(ns statspop.stats-test
  (:require  [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
             [statspop.utils-test :refer[*_*]]
             [reagent.core :as r]
             [statspop.math.stats :as stats]
             [cljs.test :as t :refer-macros [is testing]]))

(deftest test-for-devcards-true (is (= 1 (dec 2))))

(deftest chisq->p (is (*_* (stats/chisq->p 14.067 7) 0.05 3) "chi-squared p-value correct to within three significant digits"))

(deftest t->p (is (*_* (stats/t->p 1.895 7 1) 0.05 3) "one-tailed t-test p-value correct to within three significant digits"))

(deftest z->p (is (*_* (stats/z->p 1.645 1) 0.05 3) "one-tailed standardized z-test p-value correct to within three significant digits"))

(defcard-rg test-card2
  "testing devcards"
  [:div
   [:h1 "This is your second devcard!"]
   [:p (str (stats/chisq->p 14.067 7))]])
