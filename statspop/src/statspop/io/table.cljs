(ns statspop.io.table
  "display tables"
  (:require [reagent.core :as r]
            [statspop.state :refer [app-state highlight-button-when]]))

;; functions that directly set state

(defn set-table!
"sets table, and also sets default table properties to no header row, no observation name column"
  [table]
  (swap! app-state assoc
         :live-table table
         :table-properties {:header-row false :observation-names-column false}))
(defn set-table-properties!
  [prop value]
  (swap! app-state assoc-in [:table-properties prop] value))

;; components

(defn table-data-component
  "tdata is nested row-wise vectors of table data. This component displays the first 5 rows and columns for aid of identification"
  [tdata]
  (let [excerpt (take 5 (map #(for [y (take 5 %)] [:td {:key (random-uuid)} (str y)]) tdata))
        rows (for [x excerpt] [:tr {:key (random-uuid)} x])]
     [:table
      [:tbody rows]]))

(defn table-display-component
  [tdata]
  [:div (table-data-component tdata)
   [:p "Is there a header row? "
    [:button {:on-click #(set-table-properties! :header-row true)
              :style (highlight-button-when true :header-row @app-state)} " yes "]
    ;; highlight when true currently not working
    [:button {:on-click #(set-table-properties! :header-row false)} " no "]]
   [:p "Is there a column of observation names? "
    [:button {:on-click #(set-table-properties! :observation-names-column true)} " yes "]
    [:button {:on-click #(set-table-properties! :observation-names-column false)} " no "]]])

(defn table-selection-display
  "display all available tables and ask user to select one.  vector of vector of vectors"
  [tables]
  (let [t (for [tb tables]
            [:div [:hr] [table-data-component tb]
             [:p
              [:button {:on-click #(set-table! tb)}
               " choose this table "]]])
        ds (into [:div] t)]
    ds))

;; where tbody is now, could have some conditional code depending on whether there are headers... actually, no, this is the preview table.

  ; - DEV NOTE: table-properties needs to get default values on table selection.
