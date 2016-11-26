(ns statspop.ui)

;; what do I need?

;; 1.  Interface to choose data.  Display a table and such.
;; 2.  Interface to choose statistical model
;; 3.  Interface to display results of statistical model (which reminds me: I still don't have any code in here to get p-values from multiple regression---jstat probably has enough to work with.)
;; 4.  Interface to download files (csv, json)

;; 5.  charting interface

;; 6.  selector tools for table rows. 

;; workflow: stick the data in one atom, pass it to functions and display run by different atoms?
;; (maybe use re-frame architecture?)

;; need to write this bottom up.  little pieces first.

;; pathway: (a) user selects table to work with; (b) user selects what to do with it; (c) user selects columns and rows to work with, (d) user executes action.
