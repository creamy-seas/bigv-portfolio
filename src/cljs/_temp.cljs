(ns core
  (
   ;; The html expansion code
   :require-macros [hiccups.core :refer [html]]
   )
  (:require
   ;; Required for helpers to eval the expansion code in browser
   [hiccups.runtime]
   ;; csv parsing
   ["papaparse" :as Papa]
   [clojure.string :as str]
   ;; Injects the js
   [goog.dom :as gdom]))

(defn overview-view
  "Return a Hiccup vector for the overview pane.
   `rows` is a vector of [label value] pairs."
  [rows]
  [:section { :class "flex flex-col md:flex-row items-center bg-bg p-6 rounded-lg shadow-lg"}
   [:img
    {:src    "assets/profile.avif"
     :alt    "Player Photo"
     :class  (str/join
               ["block order-first rounded-[5%] "
                "w-full h-auto flex-shrink-0 mb-4 "
                "md:order-last md:mb-0 md:w-[360px] md:h-auto"])}]
   [:div.flex-1.space-y-2.md:space-y-4
    [:h2.text-2xl.font-bold "Viktor Antonov"]
    [:dl.grid.grid-cols-2.gap-y-2
   ;; mapcat = flatten the two nodes we return for every row
   (mapcat
     (fn [[label v]]
       (let [dt [:dt.font-semibold.text-myflame (str label ":")]
             val-node (if (= label "Season")
                        ;; link node
                        [:a.underline.hover:text-myflame
                         {:href (str "gallery?season=" v)} v]
                        ;; plain text
                        (str v))]
         [dt val-node]))
     rows)]]])

;; ─── CSV fetch helpers (unchanged) ────────────────────────────────────────────
;; (defn fetch-csv! [url]
;;   (js/Promise.
;;     (fn [resolve _]
;;       (Papa.parse url
;;         (clj->js {:download        true
;;                   :header          true
;;                   :dynamicTyping   true
;;                   :skipEmptyLines  true
;;                   :complete (fn [res]
;;                               (-> res .-data js->clj (keywordize-keys true) resolve))})))))

;; (defn fetch-game-stats [] (fetch-csv! "data/game_stats.csv"))
;; (defn fetch-time-log   [] (fetch-csv! "data/time_log.csv"))

;; ─── runtime entrypoint ───────────────────────────────────────────────────────
(defn ^:export init []
  (let [mount (gdom/getElement "overview")]
    (-> (js/Promise.all #js [
                             ;;(fetch-game-stats) (fetch-time-log)
                             ])
        (.then (fn [[
                     ;;game-stats time-log
                     ]]
                 (let [rows [["Age"   5]
                             ["Team"  "Slough Jets U10"]
                             ["Season" "2024-2025"]
                             ["Career Games" 1
                              ;;(count game-stats)
                              ]
                             ["Career Goals" 4
                              ;;(reduce + (map :goals        game-stats))
                              ]
                             ["Career Hours" 370
                              ;;(reduce + (map :timeOnIceH   time-log))
                              ]]]
                   (set! (.-innerHTML mount)
                         (html (overview-view rows)))))))))

;; Hot-reload hook for Figwheel
(defn ^:after-load start [] (init))

;; Run once when the page first loads
(init)

;; (ns app.core
;;   (:require
;;     ;; Import
;;     [app.highlights :refer [render-highlights]]
;;     [app.overview   :refer [render-overview]]
;;     [app.season-table :refer [render-season-table]]
;;     ))

;; (defn ^:export init []
;;   ;; call each render fn; they’ll populate their containers
;;   (render-overview)
;;   (render-highlights)
;;   (render-season-table)
;;   )
