(ns pages.landing.page
  (:require [utils.date]
            [utils.data.game-stats :as game-stats]
            [utils.data.core :as data-core]
            [utils.config :refer [config]]
            [common.template :refer [layout]]
            [pages.landing.highlights]
            [pages.landing.season-table]
            [pages.landing.gallery-link]
            [pages.landing.game-graph]
            [hiccup.page :refer [include-js]]
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
    [:section {:class "flex flex-col md:flex-row items-center p-6"}
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

(defn page []
  (let [game-stats (game-stats/read-game-stats)
        cumulative-game-stats (game-stats/eval-cumulative-game-stats game-stats)
        time-log (read-time-log)]
    (layout
     {:title       "BigV Webpage"
      :description "Tracking progress and achievements"
      :preloads [[:link {:rel "preload" :as "image" :href "/assets/profile.avif" :type "image/avif" :fetchpriority "high"}]]}
     [:container.mx-auto.px-4.space-y-8
      (overview time-log game-stats)
      (pages.landing.gallery-link/render)
      [:section.grid.grid-cols-1.md:grid-cols-2
       (pages.landing.highlights/render)
       (pages.landing.season-table/render game-stats time-log)]
      (pages.landing.game-graph/render)]
     ;; TODO: move to head
     (data-core/export-data game-stats "GAME_STATS_DATA")
     (data-core/export-data cumulative-game-stats "CUMULATIVE_GAME_STATS_DATA")
     (include-js "/js/cljs_base.js" "/js/landing.js"))))
