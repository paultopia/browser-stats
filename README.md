# browser-stats

chrome extension to capture data on a webpage and do basic data analysis on it.  Concept: detect things that look like data (tables + numeric stuff formatted like tables with divs and such), and allow users to activate a popup window to do basic data analysis on it (hypothesis tests, regressions, visualizations, etc.) and download data.  

Because the gap between "here's an interesting table" and "let's see what the relationship between those variables is" should be as small and frictionless as possible.

very much work in progress.  Most of the meat is in cljs in the statspop directory.

Currently has:

- stub of content.js for extension to extract tables from webpage (ultimately will just send data to background/popup)

- charting (scatterplots, histograms, using chartist.js)

- save as csv, json 

- basic hypothesis testing (t-tests, chi-sq tests) with the help of jstat

- basic correlation and matrix

- linear regressions (possibly unreliable, uses matrix algebra method rather than gradient dissent)

- display of tables (ugly)

- experimental inference of the presence of data in non-tabular form (hackish, ugly, untested)

- tests (via devcards) for much of the functionality

Needs: 

- basically the entire UI

- functionality to subset tables, select columns, etc.

- better representation of tables, possibly as custom data structure with optional header row and label column etc.

- chrome-specific stuff (popup.html and compilation thereto, manifest.json, etc.)

- localstorage use to persist data from accidental popup closures

- possibly fancier stats?  or at least better regression (actual p-values etc.)  (But maybe people shouldn't be using a chrome extension to do regressions and the answer should just be "you've done enough in the browser, if you're that interested download it and fire up R?")
