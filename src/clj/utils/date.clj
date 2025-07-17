(ns utils.date
  (:import [java.time LocalDate Period]))

(defn parse [^String s]
  (java.time.LocalDate/parse s))

(defn calculate-age
  "Given a birth-date string in ISO-8601 (\"yyyy-MM-dd\"), returns age in years"
  [^String bday-str]
  (let [bday (LocalDate/parse bday-str)
        today (LocalDate/now)
        years (-> (Period/between bday today)
                  .getYears)]
    years))

(defn current-season
  "Returns a map with the current hockey season as two years.
   Seasons start on September 1st. E.g. today is 2025-07-18, so
   we’re still in the 2024-2025 season."
  []
  (let [today      (LocalDate/now)
        year       (.getYear today)
        sept-first (LocalDate/of year 9 1)
        start-year (if (.isBefore today sept-first)
                     (dec year)
                     year)
        end-year   (inc start-year)]
    (str start-year "-" end-year)))
