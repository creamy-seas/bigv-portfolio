(ns pages.landing.highlights
  (:require [utils.date]
            [utils.config]
            [common.elements]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(defn read-highlights []
  (with-open [r (io/reader "data/highlights.csv")]
    (let [[headers & rows] (csv/read-csv r)
          ks (map keyword headers)
          raw-entries (map (fn [row] (zipmap ks row)) rows)
          sorted-entries (sort-by :date #(compare (utils.date/parse %2) (utils.date/parse %1)) raw-entries)]
      (map
       (fn [{:keys [date] :as entry}]
         (assoc entry :age (utils.date/calculate-age (:bday utils.config/config) date)))
       sorted-entries))))

(defn highlight-entry
  "Single highlight entry"
  [idx {:keys [date highlight age]}]
  [:li.mb-2 {:key idx}
   [:div.font-semibold
    (str "Age " age " - ")
    [:span.italic (utils.date/cast-date date "MMMM yyyy")]]
   [:div.text-sm highlight]])

(defn highlights-timeline
  "Timeline of big events!"
  []
  (let [highlights (read-highlights)]
    [:section.p-2.rounded-lg.overflow-y-scroll {:class "max-h-[500px]"}
     (common.elements/fat-title "🎉 Highlights")
     (into [:ul.p-4]
      (map-indexed highlight-entry highlights))]))
