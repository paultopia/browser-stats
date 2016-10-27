(ns statspop.charts.hist)

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

(defn hist-freqs
  [data nbins]
  (let [max (apply max data)
        min (apply min data)
        binsize (/ (- max min) nbins)
        binned-data (mapv #(* binsize (quot % binsize)) data)] ;; dirty trick to compress range to single values
    (into (sorted-map) (frequencies binned-data))))

(defn hist-keys-series [data nbins]
  (let [hist-data (hist-freqs data nbins)
        ks (mapv (comp str #(js/Math.round %)) (keys hist-data))]
    {:labels ks :series [(vec (vals hist-data))]}))

(defn make-histogram [data nbins]
    {:chart-type js/Chartist.Bar
     :data data
     :class "ct-chart ct-perfect-fourth"})

;; ABSOLUTELY MUST warn users that histogram is rough, because bin numbers
;; often don't match well to dataset sizes and shapes, uses lots of rounding
;; and might well mislead for small datasets. should be fine for big ones though.

;; I should also use transducers to make this more efficient. Way too many intermediate collections in here now. 

;; also I should later build the capacity to put multiple variables on one histogram.
