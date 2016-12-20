(ns statspop.state
  "this namespace just holds state for everything else, as a single big state atom holding a map.

  Documentation for the elements and where they're set:

  :live-table -- set in .io.table, contains (unsurprisingly) the live table of data.
  :table-properties --- set in .io.table, contains sub-map of table properties:
  - :header-row --- set in .io.table, bool, self-explanatory
  - :observation-names-column set in .io.table, bool, self-explanatory"
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {}))

(defn highlight-button-when [bool field atm] ; not currently working
  (if (boolean (:field atm))
    {:font-weight "bold" :background-color "#FFCCCC"}
    {:font-weight "normal" :background-color "white"}))
