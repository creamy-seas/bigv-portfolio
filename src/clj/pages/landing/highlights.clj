(ns pages.landing.highlights
  (:require [utils.date]
            [utils.data.highlights :refer [read-highlights]]
            [common.elements]))

(defn highlight-entry
  "Single highlight entry"
  [idx {:keys [date highlight age]}]
  [:li.mb-2 {:key idx}
   [:div.font-semibold
    (str "Age " age " - ")
    [:span.italic (utils.date/cast-date date "MMMM yyyy")]]
   [:div.text-sm highlight]])

(defn render
  "Timeline of big events!"
  []
  (let [highlights (read-highlights)]
    [:section.p-2.rounded-lg.overflow-auto
     (common.elements/fat-title "🎉 Highlights")
     (into [:ul.p-4]
           (map-indexed highlight-entry highlights))]))
