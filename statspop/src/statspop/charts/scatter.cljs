(ns statspop.charts.scatter)

(def test-data {:x [1 2 3 4 5] :y [100 200 100 40 10]})

(defn reformat-data
  "variables in {:x [1 2 3] :y [4 5 6]} form"
  [variables]
  (let [{:keys [x y]} variables]
    (->> [x y]
         (apply map vector)
         (mapv #(identity {:x (first %) :y (second %)})))))

(defn make-scatterplot [variables]
  (let [data {:series [(reformat-data variables)]}]
    (.log js/console (clj->js data))
    {:chart-type js/Chartist.Line
     :data data
     :options {:showLine false
               :axisX {:type js/Chartist.AutoScaleAxis
                       :onlyInteger true}
               :axisY {:type js/Chartist.AutoScaleAxis
                       :onlyInteger true}}
     :class "ct-chart ct-perfect-fourth"}))

;; it might be sensible to convert to standard deviation units?
;; actually would like to give users an option to do this, perhaps in the
;; function that selects the data to pass to the scatterplot function.
