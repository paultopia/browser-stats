(ns statspop.tmacros)

(defmacro rgcard
"this is just a macro to allow me to stop having to call defcard-rg and then type in a bunch of div boilerplate and such"
  [sym calculation]
  `(~'defcard-rg ~sym
     [:div
      [:p (str ~calculation)]]))

