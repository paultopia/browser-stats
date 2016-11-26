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
  [:a {:href (js/window.URL.createObjectURL (makeblob v-of-v)) :download "test.csv"} "download"])
