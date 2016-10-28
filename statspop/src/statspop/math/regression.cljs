(ns statspop.math.regression
  (:require [statspop.math.matrix :as matrix]))

(defn add-intercept
  "WARNING: this is criminally inefficient. If anyone other than me is reading this code:
  a) I know how inefficient it is.
  b) I can get away with it here because I don't expect to be dealing w/ data any bigger than
  ~ 500 rows and ~100 columns.
  c) Don't ever do this.

  vector of numeric vectors -> vector of numeric vectors"
  [vv]
  (mapv #(concat [1] %) vv))

(defn linear-regression
  "estimate OLS parameters.

  vector, nested numeric vectors --> vector"
  [labels features]
  (let [x (matrix/matrixify (add-intercept features))
        y (matrix/matrixify [labels])
        x-transpose (matrix/transpose x)
        xtx-1 (matrix/invert (matrix/multiply x-transpose x))
        xty (matrix/multiply x-transpose y)]
    (matrix/m-to-cljs (matrix/multiply xtx-1 xty))))


(def test-data
  {:labels [9.95 24.45 31.75 35.00 25.02 16.86 14.38 9.60 24.35 27.50 17.08 37.00 41.95 11.66 
            21.65 17.89 69.00 10.30 34.93 46.59 44.88 54.12 56.63 22.13 21.15]
   :features [[2 8 11 10 8 4 2 2 9 8 4 11 12 2 4 4 20 1 10 15 15 16 17 6 5]
              [50 110 120 550 295 200 375 52 100 300 412 400 500 360 205 400 600
               585 540 250 290 510 590 100 400]]})
