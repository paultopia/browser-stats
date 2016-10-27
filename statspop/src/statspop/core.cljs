(ns statspop.core
  (:require [reagent.core :as r]
            [statspop.charts.core :as c]
            [statspop.charts.scatter :as sp]
            [statspop.charts.hist :as hist]))


(def test-mini (js/jStat (clj->js [[1 2] [3 4] [5 6]])))

(defn test-jstat [arr]
  (str (js/jStat.dimensions (js/jStat arr))))

;; might just be worth rolling my own with github.com/thinktopic/aljabr for the matrix math.  except actually neither it nor thi-ng/ndarray seem to want to provide actual matrix math... there is also more js, in toji/gl-matrix. 

(.log js/console test-mini)
(.log js/console (.dimensions test-mini))





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
   [:p [:button {:on-click #(reset! c/chart-datom (sp/make-scatterplot sp/test-data))} "scatterplot"]]
   [:p [:button {:on-click #(reset! c/chart-datom (hist/make-histogram hist/tv 10))} "histogram"]]
   [c/chart-component c/chart-datom]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
