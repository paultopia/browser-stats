(ns statspop.detection
  "attempt to detect tabular data in non-table formats by finding continuous sets of rows of at least size x with at least some y proportion of numbers.  data coming in from chrome extension as documentElement.innerText

 public functions:
(DOCS TO BE WRITTEN ONCE THIS IS SEMI-STABLE)


  for actual use in the UI, the user will have to be asked to choose heuristic detection and then operate a pair of sliders to do this.

  This is the most messy namespace and will have to be. The idea is that rational people will put their tabular data in tables, but there are a lot of people who aren't rational and who put tabular data into all kinds of garbage, like <pre>, weird stuff with <div>, etc. 

  There is going to be no perfect method to detect such things.  I anticipate adding more detectors over time to get, e.g., common div formats.  It might make sense to have content.js grab everything in a <pre> tag too.
  "
  (:require [clojure.string :as s]
            [devcards.core])
  (:require-macros [devcards.core :as dc :refer [defcard deftest defcard-rg]]))

;; when this blows up, and it will, gonna need a lot more tests.

(defn transpose [m]
  (apply mapv vector m))

;; to filter by digit proportions
(defn- lines [text] (s/split text #"\n"))

(defn- spaceless [line] (s/replace line #"\s" ""))
;; need to verify this works right with tabs too.  could always have the regex match tabs too, but I think \s gets all whitespace

(defn- just-digits [line] (s/join (re-seq #"\d" line)))

(defn- identify-numeric
  "float (0,1), string --> bool
  test line to see if at least proportion of its non-space characters are numeric"
  [proportion line]
  (let [nospace (spaceless line)
        digits (just-digits nospace)]
    (>= (/ (count digits) (count nospace)) proportion)))

(defn- make-index-maps
"make vector of vectors into map with vectors and indexes"
  [vov]
  (map-indexed #(identity {:index %1 :vals %2}) vov))

(defn- get-indexes-of-matches
  "filter the index-maps by testing values against identify-numeric
  this could really be optimized with transducer. too much unnecessary intermediate collection"
  [indexed-maps proportion]
  (let [matches (filter #(identify-numeric proportion (:vals %)) indexed-maps)]
  (mapv :index matches)))

(defn- find-runs
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

(defn- get-lines-matching-indexes
  "given vector of lines and vector of indices, return subvector of lines at indices"
  [lines indices]
  (mapv (partial get lines) indices))

(defn- get-lines-from-nested-indices
  [lines nested-indices]
  (mapv (partial get-lines-matching-indexes lines) nested-indices))

(defn- gather-runs
  "takes multiline text string. splits it into lines. and then searches for lines with at least minproportion of digits in at least minruns runs.
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



;; THIS IS BROKEN.  It needs to produce nested vectors for each subset of data, AND it needs to ensure that each subset has the same number of vectors in it, i.e., same number of columns.  If nests are done by spaces, this will blow up horrifically for anything with spaces in the data, like where there are multiple word fields.

;; maybe make a simpler version that just finds pure numeric or numeric with strings at beginning?

(defn rowmaker [row]
  (into [] (remove s/blank? (s/split row #" "))))

(defn tablemaker [r]
  (mapv rowmaker r))

;; those last two functions BADLY need some kind of way to identify text columns split into words and combine them before the string split stuff happens, lest I end up creating

(defn table-validator
  "take table and make sure each row has the same number of columns (other validation steps?)"
  [nested-v]
  (apply = (map count nested-v)))

(defn split-runs
  "performs gather-runs then splits each run into nested vectors (so now the ultimate big vector has two levels of nesting). Output is vector of vector of vectors, where top-level vector consists of a series (which may be length 0 or 1) of 2d nested vectors each representing best guess at a table of data."
  [text minproportion minruns]
  (let [runs (gather-runs text minproportion minruns)]
    (mapv tablemaker runs)))

(defn identify-numeric-columns
  "takes a single table (2d vec) from split-runs, transposes to columns, and identifies numeric ones. NEEDS VALIDATION."
  [nested-v]
  (let [cols (transpose nested-v)
        digits (mapv #(mapv just-digits %) cols)]
    (mapv = cols digits)))

(defn drop-nonnumeric-columns
  "takes a single table (2d vec) from split-runs and drops all non-numeric columns.  NEEDS VALIDATION."
  [nested-v]
  (let [cols (transpose nested-v)
        digits (mapv #(mapv just-digits %) cols)
        pairs (mapv vector cols digits)
        numeric (filterv #(= (first %) (last %)) pairs)]
    (transpose (mapv #(apply flatten %) numeric))))

(defn drop-nonnumeric-except-first
  "like drop-nonnumeric (which I might not actually use), except assumes first column is descriptive text, e.g., like in a dataset where each row is a city"
  [nested-v]
  (let [cols (transpose nested-v)
        descriptors (first cols)
        mustbe-nums (rest cols)
        digits (mapv #(mapv just-digits %) mustbe-nums)
        pairs (mapv vector mustbe-nums digits)
        numeric (filterv #(= (first %) (last %)) pairs)
        flattened-numeric (map #(apply flatten %) numeric)
        combined (conj flattened-numeric descriptors)]
    (transpose combined)))
