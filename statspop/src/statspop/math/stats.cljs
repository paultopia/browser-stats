(ns statspop.math.stats
  "basic statistical tests and such: calculate test statistics, calculate p-values, etc."
  (:require [statspop.math.basic :refer [stdev mean]]))

;; need functions to actually calculate test statistics

;; todo: chisq test statistic, z-test stat, t-test stat, f-test stat.  that'll be enough.

(defn t-statistic
  "hypothesis = ostensible population mean
  observations = vector"
  [observations hypothesis]
  (let [m (mean observations)
        sd (stdev observations)
        sqn (.sqrt js/Math (count observations))]
    (/ (m - hypothesis) (/ sd sqn))))

(defn z-statistic
  "hypothesis = ostensible population mean
  observations = vector"
  [observations hypothesis]
  (let [m (mean observations)
        sd (stdev observations)]
    (/ (m - hypothesis) sd)))

;; generate p-values from standard distributions.  UNTESTED beyond api test on front page (which may be fine)

(defn chisq->p [test-stat df]
  (js/jStat.chisquare.cdf test-stat df))

(defn t->p [test-stat df tail]
  (let [one-tailed (js/jStat.studentt.cdf test-stat df)]
    (if (= 1 tail) one-tailed (* 2 one-tailed))))

(defn z->p
  "defaults to standardized normal."
  ([test-stat tail]
  (let [one-tailed (js/jStat.normal.cdf test-stat 0 1)]
    (if (= 1 tail) one-tailed (* 2 one-tailed))))
  ([test-stat distro-mean distro-sd tail]
   (let [one-tailed (js/jStat.normal.cdf test-stat distro-mean distro-sd)]
     (if (= 1 tail) one-tailed (* 2 one-tailed)))))


;; actual hypothesis tests.  These are probably the only ones that should be treated as "public" and ever make it to other namespaces.

(defn t-test
  "conduct one-sample t-test. defaults to hypothesized mean = zero and two-tailed test

  takes map of opts including :tail (1 or 2) and :hypothesis."
  ([observations]
   (let [t-stat (t-statistic observations 0)
         df (- (count observations) 1)]
     (t->p t-stat df 2)))
  ([observations opts]
   (let [t-stat (t-statistic observations (:hypothesis opts 0))
         df (- (count observations) 1)
         tail (:tail opts 2)]
     (t->p t-stat df tail))))

(defn z-test
  "conduct one-sample z-test. defaults to hypothesized mean = zero, two-tailed test, normal distribution with mean 0 and sd 1
  takes map of opts including :tail (1 or 2), :hypothesis, :mean (of distro), :sd (ditto)."
  ([observations]
   (let [z-stat (z-statistic observations 0)]
     (z->p z-stat 2)))
  ([observations opts]
   (let [z-stat (z-statistic observations (:hypothesis opts 0))
         tail (:tail opts 2)
         distro-mean (:mean opts 0)
         distro-sd (:sd opts 1)]
     (z->p z-stat distro-mean distro-sd tail))))

;; just need to add chi-square statistic + hypothesis test (and maybe f stuff) + maybe two-sample t and z then actually test these against known values --- devcards yo.
