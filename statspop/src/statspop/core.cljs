(ns statspop.core
  (:require [reagent.core :as reagent]
            [statspop.charts.core :as c]))


(def test-mini (js/jStat (clj->js [[1 2] [3 4] [5 6]])))

(defn test-jstat [arr]
  (str (js/jStat.dimensions (js/jStat arr))))

;; might just be worth rolling my own with github.com/thinktopic/aljabr for the matrix math.  except actually neither it nor thi-ng/ndarray seem to want to provide actual matrix math... there is also more js, in toji/gl-matrix. 

(.log js/console test-mini)
(.log js/console (.dimensions test-mini))


;; chart test

(defn show-line-chart
  [chart-data]
  (let [{:keys [func data options]} chart-data]
    (.log js/console (str "showing line chart with: " data))
    (new func ".ct-chart" (clj->js data) (clj->js options))))


(def test-line-data1 {:func js/Chartist.Line
                      :data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                             :series [[1 1 6 15 25]]}
                      :options {:width  "700px"
                                :height "380px"}
                       :class "ct-chart ct-perfect-fourth"})

(def test-line-data2 {:func js/Chartist.Line
                      :data {:labels ["Mar-2013" "Jun-2013" "Nov-2013" "Oct-2014" "Nov-2015"]
                             :series [[25 15 6 1 1]]}
                      :options {:width  "700px"
                                :height "380px"}
                       :class "ct-chart ct-perfect-fourth"})

(def line-datom (reagent/atom test-line-data1))

(defn line-component
  [chart-datom]
  (reagent/create-class
   {:component-did-mount #(show-line-chart @chart-datom)
    :component-did-update #(show-line-chart @chart-datom)
    :display-name        "chart-component"
    :reagent-render      (fn [chart-datom]
                           [:div {:class (:class @chart-datom)}])}))

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
  ;; [:p [:button {:on-click #(switch-line-chart line-datom)} "switch charts"]]
   ;; [line-component line-datom]
   [:p [:button {:on-click #(reset! c/chart-datom c/test-data-1)} "load data 1"]]
   [:p [:button {:on-click #(reset! c/chart-datom c/test-data-2)} "load data 2"]]
   [c/chart-component c/chart-datom]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
