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
   [:h2 "UI goes here"]
   [:p "loren ipsum"]
   ])

;; -------------------------
;; Initialize app

(defn mount-root []
  (when-let [app (.getElementById js/document "app")]
    (r/render [home-page] app)))

(defn init! []
  (mount-root))
