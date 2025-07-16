(ns utils.date)

(defn parse [^String s]
  (java.time.LocalDate/parse s))
