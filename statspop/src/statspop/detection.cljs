(ns statspop.detection
  "attempt to detect tabular data in non-table formats by finding continuous sets of rows of at least size x with at least some y proportion of numbers.  data coming in from chrome extension as documentElement.innerText

 USAGE:

  the only function that should actually be used here is gather-runs.  It takes a string of innerText from a webpage, splits it up into lines,
  and then finds consecutive runs (of user-specified size) of lines where the ratio of digits to total non-space characters is greater than some user-specified proportion.

  for actual use in the UI, the user will have to be asked to choose heuristic detection and then operate a pair of sliders to do this.
  "
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

(defn get-indexes-of-matches
  "filter the index-maps by testing values against identify-numeric
  this could really be optimized with transducer. too much unnecessary intermediate collection"
  [indexed-maps proportion]
  (let [matches (filter #(identify-numeric proportion (:vals %)) indexed-maps)]
  (mapv :index matches)))

(defn find-runs
  "given a vector of numeric indices and int minsize, returns non-overlapping vectors of runs of sequential numbers where those runs are at least minsize"
  [indices minsize]
  (loop [all-regions []
         active-region []
         cur-idx (first indices)
         remaining (rest indices)]
      (cond
        (= 0 (count remaining))
        (if (= (inc (last active-region)) cur-idx)
          (if (>= (count (conj active-region cur-idx)) minsize)
            (conj all-regions (conj active-region cur-idx))
            all-regions)
          (if (>= (count active-region) minsize)
            (conj all-regions active-region)
            all-regions)
          )
        (= active-region [])
          (recur all-regions (conj active-region cur-idx) (first remaining) (rest remaining))
        (= (inc (last active-region)) cur-idx)
          (recur all-regions (conj active-region cur-idx) (first remaining) (rest remaining))
        :else (if (>= (count active-region) minsize)
                  (recur (conj all-regions active-region) (conj [] cur-idx) (first remaining) (rest remaining))
                    (recur all-regions (conj [] cur-idx) (first remaining) (rest remaining))))))

(defn get-lines-matching-indexes
  "given vector of lines and vector of indices, return subvector of lines at indices"
  [lines indices]
  (mapv (partial get lines) indices))

(defn get-lines-from-nested-indices
  [lines nested-indices]
  (mapv (partial get-lines-matching-indexes lines) nested-indices))

(defn gather-runs
  "FINALLY!  this is the entry point to all that mess.

  takes multiline text string. splits it into lines. and then searches for lines with at least minproportion of digits in at least minruns runs.
  produces vector of runs (where each run is a vector of lines)

  text = string
  minproportion = float between 0 and 1
  minruns = integer > 0
  returns: vector of vectors, where each vector is a run of consecutive strings with at least minproportion of digits."
  [text minproportion minruns]
  (let [lns (lines text)
        index-maps (make-index-maps lns)
        indices (get-indexes-of-matches index-maps minproportion)
        runs (find-runs indices minruns)]
    (get-lines-from-nested-indices lns runs)))

;; (def test-lines "123 abc
;;   foo bar baa
;;   1234 a
;;   1245 ab
;;   1 ab
;;   12345
;;   ab
;;   12345
;;   12
;;   1 2
;;   1 a 1 2")
