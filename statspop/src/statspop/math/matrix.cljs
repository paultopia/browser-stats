(ns statspop.math.matrix
  "This namespace just wraps google closure library matrix math, because I don't want to have to remember to instantiate a bunch of classes and things.

  All functions except the first require matrix input; the first (matrixify) is constructor for matrix type. Constructor can handle nested javascript arrays or clojure vectors."
  (:require [goog.math.Matrix]))


(defprotocol IMatrixer
  (matrixify [this]))

(extend-type array
  IMatrixer
  (matrixify [this]
    (js/goog.math.Matrix. this)))

(extend-type PersistentVector
  IMatrixer
  (matrixify [this]
    (js/goog.math.Matrix. (clj->js this))))

;; according to this http://stackoverflow.com/questions/11421805/clojurescript-protocol-usage I should be able to
;; treat matrixify like a regular function here now.

(defn multiply
  "multiply matrices"
  [a-matrix another-matrix]
  (.multiply a-matrix another-matrix))

(defn transpose
  "transpose matrix"
  [matrix]
  (.getTranspose matrix))

(defn invert
  "invert matrix"
  [matrix]
  (.getInverse matrix))

(defn m-to-array
  "return matrix to javascript nested arrays"
  [matrix]
  (.toArray matrix))

(defn m-to-cljs
  "return matrix to clojurescript nested vectors"
  [matrix]
  (js->clj (.toArray matrix)))
