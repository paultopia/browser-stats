(ns statspop.core-test
  (:require  [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
             [reagent.core :as r]
             [cljs.test :as t :refer-macros [is testing]]
             [statspop.matrix-test]
             [statspop.regression-test]
             [statspop.stats-test]))
