(ns utils.url
  (:require [clojure.string :as str]
            [utils.config :refer [config]]))

(defn put-on-base
  "Return a public URL for any local asset"
  [path]
  (cond
    (re-find #"^(https?:)?//" path)  path
    (str/starts-with? path "/")         (str (:base config) path)
    :else                               (str (:base config) "/" path)))
