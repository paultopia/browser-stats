(ns statspop.charts.hist
  (:require [cljs.pprint :as pprint]))

;; test data

;; generated with (vec (sort (take 100 (repeatedly #(rand-int 50)))))

(def tv [0
         0
         2
         2
         2
         2
         4
         4
         4
         4
         5
         5
         5
         5
         5
         5
         6
         6
         6
         6
         7
         8
         8
         9
         9
         9
         9
         10
         10
         10
         11
         11
         11
         11
         11
         11
         12
         12
         13
         13
         14
         14
         15
         15
         15
         15
         15
         16
         16
         17
         18
         18
         18
         20
         20
         21
         21
         21
         22
         22
         22
         23
         23
         23
         24
         24
         25
         25
         25
         25
         26
         27
         28
         30
         30
         30
         31
         31
         32
         33
         33
         33
         33
         33
         36
         36
         37
         37
         38
         39
         40
         40
         41
         42
         42
         44
         46
         48
         48
         49])

(defn make-hist-data
  [data nbins]
  (let [max (apply max data)
        min (apply min data)
        binsize (/ (- max min) nbins)
        binned-data (mapv #(* binsize (quot % binsize)) data)] ;; dirty trick to compress range to single values
    (into (sorted-map) (frequencies binned-data))))

(defn make-hist [data nbins]
  (let [hist-data (make-hist-data data nbins)]
    ))

;; on test data, result is: {0 27, 9.8 26, 19.6 20, 29.400000000000002 17, 39.2 10}.  need to visually verify that between 0 and 9.8 is 27, between 9.8 and 19.6 is 26, etc.  Then need to map a rounding function over the keys and then to string, and I've got the x axis labels of the chart (need to note to users that it's rounded); get the values 

;; to trunc, use (pp/cl-format nil  "~,-0f" 1.2345) but strip trailing period?  or just use round from js?  
