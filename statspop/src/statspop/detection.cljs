(ns statspop.detection
  "attempt to detect tabular data in non-table formats by finding continuous sets of rows of at least size x with at least some y proportion of numbers.  data coming in from chrome extension as documentElement.innerText

 INCOMPLETE"
  (:require [clojure.string :as str]))


;; to filter by digit proportions
(defn lines [text] (str/split text #"\n"))

(defn spaceless [line] (str/replace line #"\s" ""))
;; need to verify this works right with tabs too.  could always have the regex match tabs too, but I think \s gets all whitespace

(defn just-digits [line] (str/join (re-seq #"\d" line)))

(defn identify-numeric
  "float (0,1), string --> bool
  test line to see if at least proportion of its non-space characters are numeric"
  [proportion line]
  (let [nospace (spaceless line)
        digits (just-digits nospace)]
    (>= (/ (count digits) (count nospace)) proportion)))

(defn make-index-maps
"make vector of vectors into map with vectors and indexes"
  [vov]
  (map-indexed #(identity {:index %1 :vals %2}) vov))

(defn id-num-mapped
  "convenience function for mapping "
  [proportion m]
  )

(defn get-indexes-of-matches
  "filter the index-maps by testing values against identify-numeric
  this could really be optimized with transducer. too much unnecessary intermediate collection"
  [indexed-maps proportion]
  (let [matches (filter #(identify-numeric proportion (:vals %)) indexed-maps)]
  (mapv :index matches)))

(def test-lines "123 abc
  foo bar baa
  1234 a
  1245 ab
  1 ab
  12345
  ab
  12345
  12
  1 2
  1 a 1 2")
