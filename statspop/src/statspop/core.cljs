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

(defn test-jstat-distri-api []
  (str "chi-square cdf function (test-stat->p - should be .05): "
       (js/jStat.chisquare.cdf 14.067 7)
       " | chi-square inverse function (p->test-stat - should be 14.067): "
       (js/jStat.chisquare.inv .95 7)
       " | ONE-TAILED t-test-square cdf function (test-stat->p): "
       (js/jStat.studentt.cdf 1.895 7) 
       " | ONE-TAILED t-test-square inverse function (p->test-stat): "
       (js/jStat.studentt.inv .95 7)
       " | STANDARDIZED ONE-TAILED z-test(normal)-square cdf function (test-stat->p): "
       (js/jStat.normal.cdf 1.645 0 1) 
       " | STANDARDIZED ONE-TAILED z-test(normal)-square inverse function (p->test-stat): "
       (js/jStat.normal.inv .95 0 1)
       ))

;; these above are all correct.  for p-values, I'm working with percentiles so need to subtract these percentiles from 1.  For two-tailed t-test, I should just double the p value.  Don't really need to provide p-value -> test statistic functions, should just provide test-stat to p-value functions. For z-scores, this is all standardized.  parameters for chi square and t are test statistic and degrees of freedom; parameters for normal are test statistic, mean, and sd.

;; should also add f-distro for anova. it's all in jStat.centralF and parameters are test stat, df1, and df2.  Df1 is numerator. see https://github.com/jstat/jstat/blob/master/doc/md/distributions.md 

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
   [:p (test-jstat-distri-api)]
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
