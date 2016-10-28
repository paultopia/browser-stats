(ns statspop.math.regression)

(defn add-intercept
  "WARNING: this is criminally inefficient. If anyone other than me is reading this code:
  a) I know how inefficient it is.
  b) I can get away with it here because I don't expect to be dealing w/ data any bigger than
  ~ 500 rows and ~100 columns.
  c) Don't ever do this.

  vector of numeric vectors -> vector of numeric vectors"
  [vv]
  (map #(concat [1] %) vv))
