(ns data.highlights
  (:require [utils.date   :as date]
            [utils.config :as cfg]
            [data.gallery :as gallery]
            [data.core    :as core]))

(defn read-highlights []
  (->> (core/read-csv "data/highlights.csv")
       (sort-by :date compare)
       (map
        (fn [{:keys [date] :as entry}]
          (assoc entry
                 :age (date/calculate-age
                       (:bday cfg/config) date))))))
