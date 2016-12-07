(ns statspop.io.table
  "display tables"
  (:require [reagent.core :as r]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]))

(def test-data [[1 2 3 4 5 6] [2 2 3 4 5 6] [3 2 3 4 5 6] [4 2 3 4 5 6] [5 2 3 4 5 6] [6 2 3 4 5 6]])

(defn table-display
  "tdata is nested row-wise vectors of table data. This component displays the first 5 rows and columns for aid of identification"
  [tdata]
  (let [excerpt (take 5 (map #(for [y (take 5 %)] [:td {:key (random-uuid)} (str y)]) tdata))
        rows (for [x excerpt] [:tr {:key (random-uuid)} x])]
    [:div
     [:table
      [:tbody rows]]
     [:p "Is there a header row? "
      [:button " yes "]
      [:button " no "]]
     [:p "Is there a column of observation names? "
      [:button " yes "]
      [:button " no "]]]))

;; where tbody is now, could have some conditional code depending on whether there are headers... actually, no, this is the preview table.

(defcard-rg test-table
  "testing table"
  [:div
   [:p (random-uuid)]
   [table-display test-data]])
