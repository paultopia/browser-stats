(ns statspop.charts.hist)

;; test data

;; generated with (vec (sort (take 100 (repeatedly #(rand-int 50)))))

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

(defn make-histogram [raw-data nbins]
  (let [data (hist-keys-series raw-data nbins)]
    (.log js/console (str data))
    {:chart-type js/Chartist.Bar
     :data data
     :class "ct-chart ct-perfect-fourth"}))

;; ABSOLUTELY MUST warn users that histogram is rough, because bin numbers
;; often don't match well to dataset sizes and shapes, uses lots of rounding
;; and might well mislead for small datasets. should be fine for big ones though.

;; I should also use transducers to make this more efficient. Way too many intermediate collections in here now. 

;; also I should later build the capacity to put multiple variables on one histogram.

;; actually, multiple variables should be trivially easy.  Just pass in a vector of vectors as data, map hist-keys-series over it, and pull out that last nested vector.

;; really issue is that I should require it to be normalized, say each to the range 0-1, for multiple histograms.  So maybe just a different function, or have make-histogram be multiple-arity?

;; for chartist multiple bars is trivial, so for multiple histograms I seriously just need to put the additional data as a second vector in :series.
