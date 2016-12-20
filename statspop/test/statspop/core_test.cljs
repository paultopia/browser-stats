(ns statspop.core-test
  (:require  [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
             [reagent.core :as r]
             [cljs.test :as t :refer-macros [is testing]]
             [statspop.state :refer [app-state]]
             [statspop.matrix-test]
             [statspop.math-test]
             [statspop.download-test]
             [statspop.detection-test]
             [statspop.regression-test]
             [statspop.table-test]
             [statspop.stats-test]
             [statspop.chart-test]
             [cljs.pprint :refer [pprint]]))

(defcard-rg see-app-state
  [:div [:pre (with-out-str (pprint @app-state))]])
