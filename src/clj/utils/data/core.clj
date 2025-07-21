(ns utils.data.core
  (:require [clojure.data.json :as json]))

(defn export-data
  "Supplied data is bumped to json and stored in window.[label]"
  [data ^String label]
  [:script {:type "text/javascript"}
   (str "window." label " = " (json/write-str data) ";")])
