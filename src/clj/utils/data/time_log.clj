(ns utils.data.time-log
  (:require [utils.data.core :refer [read-csv]]))

(defn read-time-log []
  (->> (read-csv "data/time_log.csv")
       (sort-by :season #(compare %2 %1))
       (map (fn [{:keys [timeOnIceH] :as entry}]
              (assoc entry :timeOnIceH (Integer/parseInt timeOnIceH))))))
