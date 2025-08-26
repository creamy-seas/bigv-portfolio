(ns pages.landing.highlights
  (:require [utils.date             :as date]
            [data.highlights  :as highlights]
            [common.elements        :as common]))

(defn highlight-entry
  "Single highlight entry"
  [idx {:keys [date highlight age]}]
  [:li.mb-2 {:key idx}
   [:div.font-semibold
    (str "Age " age " - ")
    [:span.italic (date/cast-date date "MMMM yyyy")]]
   [:div.text-sm highlight]])

(defn render
  "Timeline of big events!"
  []
  (let [highlights (highlights/read-highlights)]
    [:section.p-2.rounded-lg.overflow-auto
     (common/fat-title "🎉 Highlights")
     (into [:ul.p-4]
           (map-indexed highlight-entry highlights))]))
