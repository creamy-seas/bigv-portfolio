(ns pages.gallery.page
  (:require [utils.date]
            [common.metadata]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [hiccup2.core :as h]))

(defn read-gallery
  "Direct reading of csv with media - sorted by descending season and dates"
  []
  (with-open [r (io/reader "data/gallery.csv")]
    (let [[headers & rows] (csv/read-csv r)
          ks (map keyword headers)]
      (->> rows
           ;; Associate the keys from headers with the row values
           (map (fn [row] (zipmap ks row)))
           (map (fn [{:keys [date id type] :as entry}]
                  (assoc entry
                         :date (utils.date/parse date)
                         :thumbnail (if (= type "image")
                                      (str "https://drive.google.com/thumbnail?id=" id)
                                      "/assets/play-icon.svg")
                         )))
           (group-by :season)
           ;; Sort within each season
           (map (fn [[season entries]]
                  [season (sort-by :date entries)]))
           ;; Sort across seasons
           (into (sorted-map-by (fn [a b] (compare b a))))))))

(defn gallery-card
  "Single media element in grid view"
  [{:keys [thumbnail description date]}]
  [:div.cursor-pointer
   [:img.w-full.h-32.object-cover.rounded-lg {:src thumbnail :alt description}]
   [:p.text-sm.text-center.mt-2 description]
   [:p.text-xs.text-center.text-gray-400
    (.format (java.time.format.DateTimeFormatter/ofPattern "yyyy-MM-dd") date)]])

(defn gallery-view-no-js
  "Grid of media passed in as argument. Uses daisy-ui hiding"
  [season->entries]
  [:section.container.select-none.space-y-4
   (for [[season items] season->entries]
     [:details.collapse.collapse-arrow.rounded-none.rounded-t-lg {:key season}
      [:summary {:class "collapse-title text-xl font-semibold bg-myflame/80 text-bg"} season]
      [:div.collapse-content.p-2
       [:div.grid.grid-cols-2.sm:grid-cols-3.md:grid-cols-4.gap-4
        (->> items
             (sort-by :date #(compare %2 %1))
             (map gallery-card))]]])])

(defn gallery-view-js
  "Grid of media passed in as argument. Uses js editing of parameters"
  [season->entries]
  [:section.container.select-none
   (for [[season items] season->entries]
     [:div.collapse.collapse-arrow.rounded-none.rounded-t-lg.collapse-open {:gallery-season-key season}
      [:summary {:class "collapse-title text-xl font-semibold bg-myflame/80 text-bg"} season]
      [:div.collapse-content.p-2
       [:div.grid.grid-cols-2.sm:grid-cols-3.md:grid-cols-4.gap-4
        (->> items
             (sort-by :date #(compare %2 %1))
             (map gallery-card))]]])])

(defn -main []
  (let [entries-by-season (read-gallery)
        content (gallery-view-js entries-by-season)
        html (h/html
               [:html
                (common.metadata/head)
                [:body
                 [:div#root content]]])]
    (spit "resources/public/gallery.html" (str html))
    (println "✔ gallery.html generated")))
