(ns statspop.core
    (:require [reagent.core :as reagent]))


(def test-mini (js/jStat (clj->js [[1 2] [3 4] [5 6]])))

(defn test-jstat [arr]
  (str (js/jStat.dimensions (js/jStat arr))))

;; might just be worth rolling my own with github.com/thinktopic/aljabr for the matrix math.  except actually neither it nor thi-ng/ndarray seem to want to provide actual matrix math... there is also more js, in toji/gl-matrix. 

(.log js/console test-mini)
(.log js/console (.dimensions test-mini))


;; chart test

(defn show-chart
  []
  (let [chart-data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                    :series [[1 1 6 15 25]]}
        options {:width  "700px"
                 :height "380px"}]
    (js/Chartist.Line. ".ct-chart" (clj->js chart-data) (clj->js options)))) 


(defn chart-component
  []
  (let [some "state goes here"]
    (reagent/create-class
     {:component-did-mount #(show-chart)
      :display-name        "chart-component"
      :reagent-render      (fn []
                             [:div {:class "ct-chart ct-perfect-fourth"}])})))

;; -------------------------
;; Views

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   [:p (test-jstat [[1 2] [3 4] [5 6]])]
   [chart-component]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
