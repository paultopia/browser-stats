(ns statspop.download
  (:require [reagent.core :as r]
            [clojure.string :as s]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]))


(defn format-vec-as-csv
"n.b., this requires nested (two-dimensional) vector even if there's only one row of data. might just throw a test in for that and wrap 1-d vector or something later."
  [v-of-v]
  (s/join "\n" (mapv (partial s/join ",") v-of-v)))

(defn- makeblob
"helper function for converting nested vectors into csv blob"
  [v-of-v]
  (let [arr (clj->js [(format-vec-as-csv v-of-v)])]
    (js/Blob. arr {:type "text/csv"})))

(defn download-csv [v-of-v]
  [:a {:href (js/window.URL.createObjectURL (makeblob v-of-v)) :download "data.csv"} "download"])


(defn format-map-as-json
"what it says on the box. map to json string."
  [m] (.stringify js/JSON (clj->js m)))

(defn- makeblob2
  "to be gotten rid of after it works."
  [m]
  (let [arr (clj->js [(format-map-as-json m)])]
    (js/Blob. arr {:type "text/json"})))

(defn download-json [m]
  [:a {:href (js/window.URL.createObjectURL (makeblob2 m)) :download "data.json"} "download"])
