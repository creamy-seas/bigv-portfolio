(ns utils.data.game-stats
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(defn read-game-stats []
  (with-open [r (io/reader "data/game_stats.csv")]
    (let [[headers & rows] (csv/read-csv r)
          ks (map keyword headers)
          raw-entries (map (fn [row] (zipmap ks row)) rows)
          sorted-entries (sort-by :date #(compare %1 %2) raw-entries)]
      (map-indexed
       (fn [index {:keys [timeOnIceM goals passes shots carries takeaways] :as entry}]
         (assoc entry
                :gameNumber (+ index 1)
                :timeOnIceH (Integer/parseInt timeOnIceM)
                :goals (Integer/parseInt goals)
                :passes (Integer/parseInt passes)
                :shots (Integer/parseInt shots)
                :carries (Integer/parseInt carries)
                :takeaways (Integer/parseInt takeaways)))
       sorted-entries))))

(defn eval-cumulative-game-stats [game-stats]
  (letfn [(accumulate [remaining result running]
            (if (empty? remaining)
              result
              (let [current-stats (first remaining)
                    new {:gameNumber (:gameNumber current-stats)
                         :goals (+ (:goals running) (:goals current-stats))
                         :passes (+ (:passes running) (:passes current-stats))
                         :shots (+ (:shots running) (:shots current-stats))
                         :carries (+ (:carries running) (:carries current-stats))
                         :takeaways (+ (:takeaways running) (:takeaways current-stats))}
                    new-cum-list (conj result new)]
                (recur (rest remaining) new-cum-list new))))]
    (accumulate game-stats [] {:goals 0 :passes 0 :shots 0 :carries 0 :takeaways 0})))
