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

  nested numeric vectors, nested numeric vectors --> vector

  expects rowwise input, e.g. labels are [[lab-row-1] [lab-row-2]] and
  features are [[feat-1-row-1 feat-2-row-1] [feat-1-row-2] [feat-2-row-2]]

  to transform column-wise data in native clj datastructures, use local helper function
  cols->rows below."
  [labels features]
  (let [x (matrix/matrixify (add-intercept features))
        y (matrix/matrixify labels)
        x-transpose (matrix/transpose x)
        xtx-1 (matrix/invert (matrix/multiply x-transpose x))
        xty (matrix/multiply x-transpose y)]
    (matrix/m-to-cljs (matrix/multiply xtx-1 xty))))

(defn cols->rows
  "assumes you have a single vector of labels [l1 l2 l3]
  and nested vectors of features [[f1-row1 f1-row2 f1-row3] [f2-row1 f2-row2 f2-row3]]

  Just nests the labels in another vector and then transposes them both. "
  [labels features]
  {:labels (apply mapv vector [labels])
   :features (apply mapv vector features)})


