(ns statspop.detection-test
  (:require [statspop.detection :as d]
            [cljs.test :as t :refer-macros [is testing]]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
            [reagent.core :as r]
            [cljs.test :as t :refer-macros [is testing]]
            )
  (:require-macros [statspop.tmacros :as mac]))


(def crazy-test-block "123 ab
  foo bar baa
  1234 a
  1245 ab
  1 ab
  12345
  ab
  12345
  12
  1 2
  1 a 1 2
  123 abc
  foo bar baa
  1234 a
  1245 ab
  1 ab
  12345
  ab
  12345
  12
  1 2
  1 a 1 3
  foo
  12  25  38  91
  1992  29  38  9081
  12  24  38  91
  12  21  38  91
  12  88  38  91
  12  25  3134  91
  baz
    12 25 38 91
  1992 29 38 9081
  5512 24 38 91
  12 621 38 91
  12 88 38 91
  12 253 313444 91
  mew")

(defcard simple-gathering-runs
  "hackish test of gather-runs functionality"
  (fn []
    (let [test-lines "123 abc
  foo bar baa
  1234 a
  1245 ab
  1 ab
  12345
  ab
  12345
  12
  1 2
  1 a 1 2"]
      (d/gather-runs test-lines 0.5 3))))


(defcard simple-split-runs
  "hackish test of gather-runs functionality"
  (fn []
    (let [test-lines "123 abc
  foo bar baa
  1234 a
  1245 ab
  1 ab
  12345
  ab
  12345
  12
  1 2
  1 a 1 2"]
      (d/split-runs test-lines 0.5 3))))

(defcard-rg complicated-gathering-runs
  [:div
   [:p (str (d/gather-runs crazy-test-block 0.5 3))]])

(defcard-rg complicated-split-runs
  [:div
   [:p  (str (d/split-runs crazy-test-block 0.5 3))]])

(deftest numeric-splitter
  (is (=
       [["12" "25" "38" "91"] ["1992" "29" "38" "9081"] ["5512" "24" "38" "91"] ["12" "621" "38" "91"] ["12" "88" "38" "91"] ["12" "253" "313444" "91"]]
       (last (d/split-runs crazy-test-block 0.5 3)))))

  (def numid-test-data "12 25 38 91
  1992 29 38 9081
  5512 24 38 91a
  12 621 38 91
  12 88 38 91
  12 253 313444 91")

(defcard-rg id-numeric
  [:div
   [:p  (str (d/identify-numeric-columns (last (d/split-runs numid-test-data 0.5 3))))]])

(defcard-rg drop-nonnumeric
  [:div
   [:p  (str (d/drop-nonnumeric-columns (last (d/split-runs numid-test-data 0.5 3))))]])

;; all the boilerplate with the defcard-rgs care annoying me.

(defn easycard [calculation]
    [:div
     [:p  (str calculation)]])

(defcard-rg easycard-t (+ 1 1))

(mac/rgcard foo (+ 2 2))

(.log js/console (str (macroexpand-1 '(mac/rgcard foo (+ 2 2)))))
