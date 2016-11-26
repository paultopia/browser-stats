(ns statspop.download
  (:require [reagent.core :as r]))

;; experimenting with downloading a csv.

(def test1-text "foo,bar")

(defn makeblob [csv]
  (js/Blob. (clj->js [csv]) {:type "text/csv"}))

(defn download-csv [csv]
  [:a {:href (js/window.URL.createObjectURL (makeblob csv)) :download "test.csv"} "download"])
