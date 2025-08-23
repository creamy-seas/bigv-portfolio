(ns pages.gallery.gallery-grid
  (:require [utils.data.gallery :as gallery]))

(defn gallery-card
  "Single media element in grid view"
  [{:keys [thumbnail description date gallery-idx]}]
  [:div.gallery-card.cursor-pointer {:gallery-idx gallery-idx}
   [:img.w-full.h-32.object-cover.rounded-lg {:src thumbnail :alt description}]
   [:p.text-sm.text-center.mt-2 description]
   [:p.text-xs.text-center.text-gray-400 date]])

(defn render-js
  "Grid of media elements passed in as list in argument.
  They are grouped by season and placed in a collapsible element"
  [gallery-data]
  [:section#gallery-grid.container.select-none
   (for [[season items] (gallery/group-gallery gallery-data)]
     [:div.collapse.collapse-arrow.rounded-none.rounded-t-lg {:gallery-season-key season}
      [:summary {:class "collapse-title text-xl font-semibold bg-myflame/80 text-bg"} season]
      [:div.collapse-content.p-2
       [:div.grid.grid-cols-2.sm:grid-cols-3.md:grid-cols-4.gap-4
        (map gallery-card items)]]])])

;; TODO display this an gallery cards that when clicked just open up a url
;; (defn gallery-grid-no-js
;; "Grid of media elements passed in as list in argument.
;; They are grouped by season and placed in a collapsible element
;; - As JS is disabled - uses the default daisy-ui hiding mechanism"
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
