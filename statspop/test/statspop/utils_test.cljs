(ns statspop.utils-test
  (:require  [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
             [reagent.core :as r]
             [statspop.math.stats :as stats]
             [cljs.test :as t :refer-macros [is testing]]))

(defn *_*
  "approximate equality function to handle floating point inaccuracy + rounding from statistical tables, whatever weird errors come from doing calculus in js, etc. Will test inequality to digits significant digits"
  [form1 form2 digits]
  (let [diff (.abs js/Math (- form1 form2))]
    (< diff (.pow js/Math 10 (- digits)))))

(deftest testing-the-approximate-equality
  (is (*_* 1.113 1.115 2)))

(deftest testing-the-approximate-equality-failing
  (is (false? (*_* 1.1113 1.1115 5))))
