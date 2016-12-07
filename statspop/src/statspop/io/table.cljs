(ns statspop.io.table
  "display tables"
  (:require [reagent.core :as r]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]))

(def test-data [[1 2 3 4 5 6] [2 2 3 4 5 6] [3 2 3 4 5 6] [4 2 3 4 5 6] [5 2 3 4 5 6] [6 2 3 4 5 6]])

(defn table-display
  "tdata is nested row-wise vectors of table data. This component displays the first 5 rows and columns for aid of identification"
  [tdata]
  (let [excerpt (take 5 (map #(take 5 %) tdata))]
    [:p (str excerpt)]))


(defcard-rg test-table
  "testing table"
  [:div
   [:p 
   [table-display test-data]]])
