(ns statspop.chart-test
  (:require [statspop.charts.core :as c]
            [statspop.charts.scatter :as sp]
            [statspop.charts.hist :as hist]
            [cljs.test :as t :refer-macros [is testing]]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [reagent.core :as r]
            [cljs.test :as t :refer-macros [is testing]]
            ))


(def line-data-1 {:chart-type js/Chartist.Line
                  :data {:labels ["Mar-2012" "Jun-2012" "Nov-2012" "Oct-2013" "Nov-2014"]
                         :series [[1 1 6 15 25]]}
                  :options {:width  "700px"
                            :height "380px"}
                  :class "ct-chart ct-perfect-fourth"})

(def line-data-2 {:chart-type js/Chartist.Line
                  :data {:labels ["Mar-2013" "Jun-2013" "Nov-2013" "Oct-2014" "Nov-2015"]
                         :series [[25 15 6 1 1]]}
                  :options {:width  "700px"
                            :height "380px"}
                  :class "ct-chart ct-perfect-fourth"})

(def scatter-data {:x [1 2 3 4 5] :y [100 200 100 40 10]})

(def hist-data [0
         0
         2
         2
         2
         2
         4
         4
         4
         4
         5
         5
         5
         5
         5
         5
         6
         6
         6
         6
         7
         8
         8
         9
         9
         9
         9
         10
         10
         10
         11
         11
         11
         11
         11
         11
         12
         12
         13
         13
         14
         14
         15
         15
         15
         15
         15
         16
         16
         17
         18
         18
         18
         20
         20
         21
         21
         21
         22
         22
         22
         23
         23
         23
         24
         24
         25
         25
         25
         25
         26
         27
         28
         30
         30
         30
         31
         31
         32
         33
         33
         33
         33
         33
         36
         36
         37
         37
         38
         39
         40
         40
         41
         42
         42
         44
         46
         48
         48
         49])

(defcard-rg test-card
  "the buttons should create pretty charts!"
  [:div
   [:p [:button {:on-click #(reset! c/chart-datom line-data-1)} "load data 1"]]
   [:p [:button {:on-click #(reset! c/chart-datom line-data-2)} "load data 2"]]
   [:p [:button {:on-click #(reset! c/chart-datom (sp/make-scatterplot scatter-data))} "scatterplot"]]
   [:p [:button {:on-click #(reset! c/chart-datom (hist/make-histogram hist-data 10))} "histogram"]]
   [c/chart-component c/chart-datom]])

