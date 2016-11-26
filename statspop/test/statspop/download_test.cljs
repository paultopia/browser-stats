(ns statspop.download-test
  (:require [statspop.download :as d]
            [cljs.test :as t :refer-macros [is testing]]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [reagent.core :as r]
            [cljs.test :as t :refer-macros [is testing]]
            ))

(deftest nested-vectors-to-csv-string
  (is (=
       (d/format-vec-as-csv [["foo" "bar"] [1 2] ["baz" 3]])
       "foo,bar\n1,2\nbaz,3")))

(deftest map-to-json-string
  (is (=
       (d/format-map-as-json {:foo "bar" :baz 1})
       "{\"foo\":\"bar\",\"baz\":1}")))

(defcard-rg download-csv
  "download a csv file named data.csv containing the contents of the csv test above"
  [:div
   [:p
    [d/download-csv [["foo" "bar"] [1 2]]]]])

(defcard-rg download-json
  "download a json file named data.json containing the contents of the json test above"
  [:div
   [:p
    [d/download-json {:foo "bar" :baz 1}]]])
