(ns statspop.charts.core
  (:require [reagent.core :refer [atom create-class]]))

(def chart-datom (atom nil))

(defn show-chart
  [chart-data]
  (let [{:keys [chart-type data options]} chart-data]
    (new chart-type ".ct-chart" (clj->js data) (clj->js options))))

(defn chart-component
  [chart-datom]
  (create-class
   {:component-did-update #(show-chart @chart-datom)
    :display-name        "chart-component"
    :reagent-render      (fn [chart-datom]
                           [:div {:class (:class @chart-datom)}])}))

;; this is going to throw an error whenever the chart is taken down unless I find some way to replace it with a blank chart or blank component.  Or just not have an option to take it down?  because why bother, it'll be reset for any new page or table anyway. 
