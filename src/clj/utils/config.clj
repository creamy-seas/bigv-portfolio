(ns utils.config
  (:require
    [clojure.edn     :as edn]
    [clojure.java.io :as io]
    [utils.date :refer [calculate-age current-season]]))

(defonce config
  (let [base (-> "config.edn"
      io/resource
      slurp
      edn/read-string)]
    (-> base
        (assoc :age (calculate-age (:bday base)))
        (assoc :season (current-season)))))
