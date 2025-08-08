(ns pages.landing.page
  (:require [utils.url :refer [put-on-base]]
            [utils.data.game-stats :refer [eval-cumulative-game-stats read-game-stats]]
            [utils.data.core :refer [export-data]]
            [utils.data.time-log :refer [read-time-log]]
            [utils.config :refer [config]]
            [common.template :refer [layout]]
            [pages.landing.highlights]
            [pages.landing.overview]
            [pages.landing.season-table]
            [pages.landing.gallery-link]
            [pages.landing.game-graph]
            [hiccup.page :refer [include-js]]))

(def profile-image-preload
  [:link
   {:rel "preload"
    :as "image"
    :href (put-on-base "/assets/profile.avif")
    :type "image/avif"
    :fetchpriority "high"}])

(defn render []
  (let [time-log (read-time-log)
        game-stats (read-game-stats)
        cumulative-game-stats (eval-cumulative-game-stats game-stats)]
    (layout
     {:title (:title-tag-landing config)
      :description "Tracking progress and achievements"
      :extra-elements [profile-image-preload
                       (export-data game-stats "GAME_STATS_DATA")
                       (export-data cumulative-game-stats "CUMULATIVE_GAME_STATS_DATA")]}
     [:container.mx-auto.px-4.space-y-8
      (pages.landing.overview/render)
      (pages.landing.gallery-link/render)
      [:section.grid.grid-cols-1.md:grid-cols-2
       (pages.landing.highlights/render)
       (pages.landing.season-table/render game-stats time-log)]
      (pages.landing.game-graph/render)]
     ;; Load these after html is parsed to avoid using defer etc
     (include-js (put-on-base "/extern/chart.js")
                 (put-on-base "/js/cljs_base.js")
                 (put-on-base "/js/landing.js")))))
