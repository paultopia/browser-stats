(ns statspop.core
  (:require [reagent.core :as r]
            [statspop.charts.core :as c]
            [cljs.test :as t :refer-macros [is testing]]
            [statspop.charts.scatter :as sp]
            [statspop.charts.hist :as hist]
            [statspop.math.matrix :as matrix]
            [statspop.detection :as detection]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [statspop.download :refer [download-csv]]
            ))


(defcard-rg test-card
  "testing devcards"
   [:div
    [:h1 "This is your first devcard!"]])


;; -------------------------
;; Views

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   [:p (download-csv "foo,bar")]
   [:p [:button {:on-click #(reset! c/chart-datom c/test-data-1)} "load data 1"]]
   [:p [:button {:on-click #(reset! c/chart-datom c/test-data-2)} "load data 2"]]
   [:p [:button {:on-click #(reset! c/chart-datom (sp/make-scatterplot sp/test-data))} "scatterplot"]]
   [:p [:button {:on-click #(reset! c/chart-datom (hist/make-histogram hist/tv 10))} "histogram"]]
   [c/chart-component c/chart-datom]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (when-let [app (.getElementById js/document "app")]
    (r/render [home-page] app)))

(defn init! []
  (mount-root))
