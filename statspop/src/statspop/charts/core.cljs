(ns statspop.charts.core
  (:require [reagent.core :refer [atom create-class]]))

;; test data to be deleted in due course and replaced with calls to constructor functions from other namespaces

(def test-data-1 {:chart-type js/Chartist.Line
                  :data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                         :series [[1 1 6 15 25]]}
                  :options {:width  "700px"
                            :height "380px"}
                  :class "ct-chart ct-perfect-fourth"})

(def test-data-2 {:chart-type js/Chartist.Line
                       :data {:labels ["Mar-2013" "Jun-2013" "Nov-2013" "Oct-2014" "Nov-2015"]
                              :series [[25 15 6 1 1]]}
                       :options {:width  "700px"
                                 :height "380px"}
                       :class "ct-chart ct-perfect-fourth"})

;; core functions to retain.

(def chart-datom (atom nil))
;; this throws an error on initial render because there isn't a chart-type to call. I'm ok with that.  It doesn't crash.  though maybe I can try it with bogus data?  or just catch the error.


(defn show-chart
  [chart-data]
  (let [{:keys [chart-type data options]} chart-data]
    (new chart-type ".ct-chart" (clj->js data) (clj->js options))))

(defn chart-component
  [chart-datom]
  (create-class
   {:component-did-mount #(show-chart @chart-datom)
    :component-did-update #(show-chart @chart-datom)
    :display-name        "chart-component"
    :reagent-render      (fn [chart-datom]
                           [:div {:class (:class @chart-datom)}])}))
