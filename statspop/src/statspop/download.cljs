(ns statspop.download
  "downloaders for data collected from webpage, in either csv or json (json should be used only if there are header rows, and user should be asked to confirm header rows first)"
  (:require [reagent.core :as r]
            [clojure.string :as s]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [cljs.spec :as spec]))

(defn format-map-as-json
  "what it says on the box. map to json string."
  [m] (.stringify js/JSON (clj->js m)))

(spec/fdef format-map-as-json
           :args map?
           :ret string?)

(defn format-vec-as-csv
"n.b., this requires nested (two-dimensional) vector even if there's only one row of data. might just throw a test in for that and wrap 1-d vector or something later."
  [v-of-v]
  (s/join "\n" (mapv (partial s/join ",") v-of-v)))

(spec/fdef format-vec-as-csv
           :args (spec/coll-of vector? :kind vector?)
           :ret string?)

(defn- makeblob
"helper function for converting clj datastructure into appropriate blob.  type is :csv or :json"
  [v-of-v type]
  (let [formatter (type {:csv format-vec-as-csv
                         :json format-map-as-json})
        typestring (str "text/" (name type))
        arr (clj->js [(formatter v-of-v)])]
    (js/Blob. arr {:type typestring})))

(defn downloader
  "make download link for data. args are either 2d nested vectors and :csv or map and :json"
  [data type]
  (let [blob (makeblob data type)
        filename (str "data." (name type))]
    [:a {:href (js/window.URL.createObjectURL blob) :download filename} "download"]))
