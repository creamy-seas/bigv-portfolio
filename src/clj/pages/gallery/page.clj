(ns pages.gallery.page
  (:require [utils.date]
            [common.metadata]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [hiccup.page :refer [html5 include-js]]
            ))

(defn read-gallery
  "Direct reading of csv with media - sorted by descending dates"
  []
  (with-open [r (io/reader "data/gallery.csv")]
    (let [[headers & rows] (csv/read-csv r)
          ks (map keyword headers)
          raw-entries (map (fn [row] (zipmap ks row)) rows)
          sorted-entries (sort-by
                          :date
                          #(compare (utils.date/parse %2) (utils.date/parse %1))
                          raw-entries)
          entries-with-meta (map-indexed
                             (fn [idx {:keys [id type] :as entry}]
                               (assoc entry
                                      :idx idx
                                      :thumbnail (if (= type "image")
                                                   (str "https://drive.google.com/thumbnail?id=" id)
                                                   "/assets/play-icon.svg")
                                      :iframe    (str "https://drive.google.com/file/d/"
                                                      id
                                                      "/preview?autoplay=1")))
                             sorted-entries)]
      entries-with-meta)))

(defn group-gallery
  "Return the gallery entires grouped by season descending"
  [gallery-data]
  (into (sorted-map-by (fn [a b] (compare b a))) (group-by :season gallery-data)))

(defn gallery-card
  "Single media element in grid view"
  [{:keys [thumbnail description date idx]}]
  [:div.gallery-card.cursor-pointer {:key idx}
   [:img.w-full.h-32.object-cover.rounded-lg {:src thumbnail :alt description}]
   [:p.text-sm.text-center.mt-2 description]
   [:p.text-xs.text-center.text-gray-400 date]])

;; (defn gallery-view-no-js
;;   "Grid of media passed in as argument. Uses daisy-ui hiding"
;;   [gallery-data]
;;   [:section.container.select-none.space-y-4
;;    (for [[season items] gallery-data]
;;      [:details.collapse.collapse-arrow.rounded-none.rounded-t-lg {:key season}
;;       [:summary {:class "collapse-title text-xl font-semibold bg-myflame/80 text-bg"} season]
;;       [:div.collapse-content.p-2
;;        [:div.grid.grid-cols-2.sm:grid-cols-3.md:grid-cols-4.gap-4
;;         (->> items
;;              (sort-by :date #(compare %2 %1))
;;              (map gallery-card))]]])])

(defn gallery-grid-js
  "Grid of media passed in as argument. Uses js editing of parameters"
  [gallery-data]
  [:section#gallery-grid.container.select-none
   (for [[season items] (group-gallery gallery-data)]
     [:div.collapse.collapse-arrow.rounded-none.rounded-t-lg {:gallery-season-key season}
      [:summary {:class "collapse-title text-xl font-semibold bg-myflame/80 text-bg"} season]
      [:div.collapse-content.p-2
       [:div.grid.grid-cols-2.sm:grid-cols-3.md:grid-cols-4.gap-4
        (map gallery-card items)]]])])

(def nav-arrow-class "absolute top-1/2 -translate-y-1/2 w-12 h-40
                    flex items-center justify-center
                    bg-fg/20 text-bg/40 ring-myflame/20 ring-2
                    hover:bg-myflame hover:text-bg active:bg-myflame active:text-bg
                    transition-colors duration-200")

(defn gallery-modal
  "A popup with large display of google content in iframe"
  []
  [:div#gallery-modal
   {:class "fixed flex hidden
            items-center justify-center p-4
            bg-black/75  inset-0 z-50"}
   [:div.modal-content
    {:class    "relative bg-bg
                p-6 rounded-lg
                w-full lg:max-w-[70vw]
                overflow-auto"
     :onclick "event.stopPropagation()"}
    [:button#gallery-modal-prev.left-4  {:class (str nav-arrow-class " left-2 rounded-tl-xl rounded-bl-xl rounded-tr-sm rounded-br-sm")}
     [:span.text-3xl "‹"]]
    [:button#gallery-modal-next.right-4 {:class (str nav-arrow-class " right-2 rounded-tl-xl rounded-bl-xl rounded-tr-sm rounded-br-sm")}
     [:span.text-3xl "›"]]
    [:div.w-full.aspect-video
     [:iframe#gallery-modal-iframe.w-full.h-full
      {:src             ""
       :allow           "autoplay"
       :allowfullscreen ""
       :title           ""}]]
    [:h3#gallery-modal-description.text-xl.font-semibold.text-myflame.mt-4 ""]
    [:p#gallery-modal-date.text-sm.text-gray-400 ""]]]
  )

(defn export-data
  "TODO: move"
  [gallery-data]
  [:script
   (str "window.GALLERY_DATA = "
        (json/write-str gallery-data)
        ";")
   (str "window.GALLERY_DATA_MAX_IDX = "
        (- (count gallery-data) 1)
        ";")])

(defn html []
  "Main html of page"
  (html5
   (common.metadata/head)
   (let [gallery-data (read-gallery)]
     [:body
      (gallery-grid-js gallery-data)
      (gallery-modal)
      (export-data gallery-data)
      (include-js "/js/gallery.js")])))

(defn -main []
  (spit "resources/public/gallery.html" (html))
  (println "✔ gallery.html generated"))
