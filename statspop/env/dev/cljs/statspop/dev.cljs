(ns ^:figwheel-no-load statspop.dev
  (:require [statspop.core :as core]
            [statspop.core-test]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback core/mount-root)

(core/init!)
