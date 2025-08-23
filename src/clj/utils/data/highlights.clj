(ns utils.data.highlights
  (:require [utils.date]
            [utils.config]
            [utils.data.core :refer [read-csv]]))

(defn read-highlights []
  (->> (read-csv "data/highlights.csv")
       (sort-by :date compare)
       (map
        (fn [{:keys [date] :as entry}]
          (assoc entry
                 :age (utils.date/calculate-age
                       (:bday utils.config/config)
                       date))))))
