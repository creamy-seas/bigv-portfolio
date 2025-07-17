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
          entries (->> rows
           ;; Associate the keys from headers with the row values
           (map (fn [row] (zipmap ks row)))
           (map (fn [{:keys [id type] :as entry}]
                  (assoc entry
                         :thumbnail (if (= type "image")
                                      (str "https://drive.google.com/thumbnail?id=" id)
                                      "/assets/play-icon.svg")
                         :iframe (str "https://drive.google.com/file/d/" id "/preview")
                         ))))]
      ;; Return sorted list
      (sort-by :date #(compare (utils.date/parse %2) (utils.date/parse %1)) entries))))

(defn group-gallery
  "Return the gallery entires grouped by season descending"
  [gallery-data]
  (into (sorted-map-by (fn [a b] (compare b a))) (group-by :season gallery-data)))

(defn gallery-card
  "Single media element in grid view"
  [{:keys [thumbnail description date]}]
  [:div.cursor-pointer
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
  [:section.container.select-none
   (for [[season items] (group-gallery gallery-data)]
     [:div.collapse.collapse-arrow.rounded-none.rounded-t-lg {:gallery-season-key season}
      [:summary {:class "collapse-title text-xl font-semibold bg-myflame/80 text-bg"} season]
      [:div.collapse-content.p-2
       [:div.grid.grid-cols-2.sm:grid-cols-3.md:grid-cols-4.gap-4
        (map gallery-card items)]]])])

;; (defn gallery-modal
;;   "A popup with large display of google content in iframe"
;;   [:div#modal
;;    {:class "hidden fixed flex
;;             items-center justify-center p-4
;;             bg-black/75  inset-0 z-50"
;;     :onclick "closeModal(event)"}
;;    [:div.modal-content
;;     {:class   "relative bg-bg p-6 rounded-lg max-w-[90vw] max-h-[90vh]
;;                w-full sm:w-[600px] overflow-auto"
;;      :onclick "event.stopPropagation()"}
;;     [:button.nav-arrow.left-4  {:onclick "showPrev(event)"}  "‹"]
;;     [:button.nav-arrow.right-4 {:onclick "showNext(event)"} "›"]
;;     [:div.w-full.aspect-video
;;      [:iframe#modal-iframe
;;       {:src             ""
;;        :allow           "autoplay"
;;        :allowfullscreen ""
;;        :title           ""}]]
;;     [:h3#modal-description.text-xl.font-semibold.text-myflame.mt-4 ""]
;;     [:p#modal-date.text-sm.text-gray-400 ""]]]
;;   )

(defn export-data
  "TODO: move"
  [season->entries]
  [:script
   (str "window.GALLERY_DATA = "
        (json/write-str season->entries)
        ";")])

(defn html []
  "Main html of page"
  (html5
   (common.metadata/head)
   (let [gallery-data (read-gallery)]
     [:body
      (gallery-grid-js gallery-data)
      ;;(gallery-modal)
      (export-data gallery-data)
      (include-js "/js/gallery.js")])))

(defn -main []
  (spit "resources/public/gallery.html" (html))
  (println "✔ gallery.html generated"))
