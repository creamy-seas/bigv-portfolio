(ns pages.landing.page
  (:require [utils.date]
            [utils.config :refer [config]]
            [common.template :refer [layout]]
            [pages.landing.highlights]
            [pages.landing.season-table]
            [pages.landing.gallery-link]
            [pages.landing.game-graph]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(defn overview [time-log game-stats]
  (let [info [["Age"   (:age config)]
              ["Team"  (:team config)]
              ["Season" (:season config)]
              ["Career Games" (count game-stats)]
              ["Career Goals"
               (apply + (map :goals game-stats))]
              ["Career Hours"
               (apply + (map :timeOnIceH time-log))]]]
    [:section {:class "flex flex-col md:flex-row items-center bg-bg p-6 rounded-lg shadow-lg"}
     [:img
      {:src    "/assets/profile.avif"
       :alt    "Player Photo"
       :class  "block order-first rounded-[5%]
              w-full h-auto flex-shrink-0 mb-4
              md:order-last md:mb-0 md:w-[360px] md:h-auto"}]
     [:div.flex-1.space-y-2.md:space-y-4
      [:h2.text-2xl.font-bold "Viktor Antonov"]
      [:dl.grid.grid-cols-2.gap-y-2
       ;; mapcat = flatten the two nodes we return for every row
       (mapcat
        (fn [[label v]]
          (let [dt [:dt.font-semibold.text-myflame (str label ":")]
                val-node (if (= label "Season")
                           [:a.underline.hover:text-myflame
                            {:href (str "gallery?season=" v)} v]
                           (str v))]
            [dt val-node]))
        info)]]]))

(defn read-time-log []
  (with-open [r (io/reader "data/time_log.csv")]
    (let [[headers & rows] (csv/read-csv r)
          ks (map keyword headers)
          raw-entries (map (fn [row] (zipmap ks row)) rows)
          sorted-entries (sort-by :season #(compare %2 %1) raw-entries)]
      (map
       (fn [{:keys [timeOnIceH] :as entry}]
         (assoc entry
                :timeOnIceH (Integer/parseInt timeOnIceH)))
       sorted-entries))))

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

(defn page []
  (let [game-stats (read-game-stats)
        time-log (read-time-log)]
    (layout
     {:title       "BigV Webpage"
      :description "Tracking progress and achievements"
      :preloads [[:link {:rel "preload" :as "image" :href "/assets/profile.avif" :type "image/avif" :fetchpriority "high"}]]}
     [:container.mx-auto.px-4.space-y-8
      (overview time-log game-stats)
      (pages.landing.gallery-link/render)
      [:div.grid.grid-cols-1.md:grid-cols-2.gap-2
       (pages.landing.highlights/render)
       (pages.landing.season-table/render game-stats time-log)]
      (pages.landing.game-graph/render)])))
