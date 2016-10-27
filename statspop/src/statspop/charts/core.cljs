(ns statspop.charts.core
  (:require [reagent.core :as r]))

(def chart-datom (r/atom nil))

(defn show-line-chart
  [chart-data]
  (let [{:keys [func data options]} chart-data]
    (func ".ct-chart" (clj->js data) (clj->js options))))

(def test-data-1 {:func js/Chartist.Line.
                  :data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                              :series [[1 1 6 15 25]]}
                       :options {:width  "700px"
                                 :height "380px"}
                       :class "ct-chart ct-perfect-fourth"})

(def test-line-data2b {:data {:labels ["Mar-2013" "Jun-2013" "Nov-2013" "Oct-2014" "Nov-2015"]
                              :series [[25 15 6 1 1]]}
                       :options {:width  "700px"
                                 :height "380px"}
                       :class "ct-chart ct-perfect-fourth"})
