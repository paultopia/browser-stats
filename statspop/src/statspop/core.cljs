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

;; let's experiment some more with charts eh?  "datom" == data atom

(def test-line-data1 {:data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                             :series [[1 1 6 15 25]]}
                      :options {:width  "700px"
                                :height "380px"}})

(def test-line-data2 {:data {:labels ["Mar-2013" "Jun-2013" "Nov-2013" "Oct-2014" "Nov-2015"]
                             :series [[25 15 6 1 1]]}
                      :options {:width  "700px"
                                :height "380px"}})

(def line-datom (reagent/atom test-line-data1))

(defn show-line-chart
  [chart-data]
  (let [{:keys [data options]} chart-data]
    (.log js/console (str "showing line chart with: " data))
    (js/Chartist.Line. ".ct-chart" (clj->js data) (clj->js options))))

(defn line-component
  [chart-data chart-datom]
    (reagent/create-class
     {:component-did-mount #(show-line-chart @chart-datom)
      :component-will-update #(show-line-chart @chart-datom)
      :component-will-receive-props #(show-line-chart @chart-datom)
      :component-did-update #(show-line-chart @chart-datom)
      :display-name        "chart-component"
      :reagent-render      (fn []
                             [:div {:class "ct-chart ct-perfect-fourth"}])}))

(defn switch-line-chart [datom]
  (let [chart-state @datom
        new-datom (condp = chart-state
      test-line-data1 test-line-data2
      test-line-data2 test-line-data1)]
    (.log js/console (str "switching to: " new-datom))
    (reset! datom new-datom)
    (.log js/console (str "data now is: " @datom))))


;; -------------------------
;; Views

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   [:p (test-jstat [[1 2] [3 4] [5 6]])]
   [:p [:button {:on-click #(switch-line-chart line-datom)} "switch charts"]]
   [line-component @line-datom line-datom]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
