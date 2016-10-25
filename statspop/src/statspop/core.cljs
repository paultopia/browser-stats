(ns statspop.core
    (:require [reagent.core :as reagent]))


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
   [:p (test-jstat [[1 2] [3 4] [5 6]])]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
