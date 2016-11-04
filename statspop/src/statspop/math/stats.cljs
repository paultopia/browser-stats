(ns statspop.math.stats
  "basic statistical tests and such: calculate test statistics, calculate p-values, etc.")

;; need functions to actually calculate test statistics

;; todo: chisq test statistic, z-test stat, t-test stat, f-test stat.  that'll be enough.


;; generate p-values from standard distributions.  UNTESTED beyond api test on front page (which may be fine)

(defn chisq->p [test-stat df]
  (js/jStat.chisquare.cdf test-stat df))

(defn t->p [test-stat df tails]
  (let [one-tailed (js/jStat.studentt.cdf test-stat df)]
    (if (= 1 tails) one-tailed (* 2 one-tailed))))

(defn z->p
  "defaults to standardized normal."
  ([test-stat tails]
  (let [one-tailed (js/jStat.normal.cdf test-stat 0 1)]
    (if (= 1 tails) one-tailed (* 2 one-tailed))))
  ([test-stat mean sd tails]
   (let [one-tailed (js/jStat.normal.cdf test-stat mean sd)]
     (if (= 1 tails) one-tailed (* 2 one-tailed)))))
