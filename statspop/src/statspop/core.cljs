(ns statspop.core
  (:require [reagent.core :as r]
            [statspop.charts.core :as c]
            [statspop.charts.scatter :as sp]
            [statspop.charts.hist :as hist]
            [statspop.math.matrix :as matrix]
            [statspop.detection :as detection]
            [devcards.core])
  (:require-macros [devcards.core :as dc :refer [defcard deftest defcard-rg]]))


;; experimenting with jstat
(def test-mini (js/jStat (clj->js [[1 2] [3 4] [5 6]])))

(defn test-jstat [arr]
  (str (js/jStat.dimensions (js/jStat arr))))

(defcard-rg test-card
  "testing devcards"
   [:div
    [:h1 "This is your first devcard!"]])

;; (.log js/console test-mini)
;; (.log js/console (.dimensions test-mini))

;; experimenting with google closure math

(def test-mat1 [[1 2 3] [4 5 6]])

(def test-mat2 (clj->js [[7 8 9] [10 11 12]]))

(.log js/console (-> test-mat1 matrix/matrixify matrix/transpose matrix/m-to-cljs str))

(.log js/console (-> test-mat2 matrix/matrixify matrix/transpose matrix/m-to-cljs str))

;; ok matrix operations with google closure seem to want to behave.

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
