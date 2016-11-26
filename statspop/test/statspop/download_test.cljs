(ns statspop.download-test
  (:require [statspop.download :as d]
            [cljs.test :as t :refer-macros [is testing]]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [reagent.core :as r]
            [cljs.test :as t :refer-macros [is testing]]
            ))

(deftest nested-vectors-to-csv
  (is (=
       (d/format-vec-as-csv [["foo" "bar"] [1 2] ["baz" 3]])
       "foo,bar\n1,2\nbaz,3")))

(defcard-rg test-card
  "download a csv file named test.csv containing the contents of the test above"
  [:div
   [:p
    [d/download-csv [["foo" "bar"] [1 2]]]]])
