(ns statspop.table-test
  (:require [statspop.io.table :as table]
            [statspop.state :refer [app-state]]
            [cljs.test :as t :refer-macros [is testing]]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [reagent.core :as r]
            [cljs.test :as t :refer-macros [is testing]])
  (:require-macros [statspop.tmacros :as mac :refer [rgcard]]))

(def test-data [[1 2 3 4 5 6] [2 2 3 4 5 6] [3 2 3 4 5 6] [4 2 3 4 5 6] [5 2 3 4 5 6] [6 2 3 4 5 6]])

(def test-coll-tables [test-data (into [] (rseq test-data))])

; (def test-coll-tables (vec (repeat 2 test-data)))

(defcard-rg test-table
  "testing individual table display"
  [:div
   [table/table-display-component (:live-table @app-state)]])

(defcard-rg test-table-selection
  "testing table selection"
  [:div
   [table/table-selection-display test-coll-tables]])
