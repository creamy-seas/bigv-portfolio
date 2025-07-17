(ns gallery.collapse
  (:require [goog.events :as events]))

(defn $$
  "Return a Clojure seq of all nodes that match `sel`."
  [sel]
  (array-seq (.querySelectorAll js/document sel)))

(defn cls [el] (.-classList el))
(defn open!   [el] (.add    (cls el) "collapse-open"))
(defn close!  [el] (.remove (cls el) "collapse-open"))
(defn toggle!  [el] (.toggle (cls el) "collapse-open"))

(defn click-handler [e]
  (let [box
        (.closest
         (.-currentTarget e)
         ".collapse[gallery-season-key]")]
    (doseq [c ($$ ".collapse[gallery-season-key]")
            :when (not= c box)]
      (close! c))
    (toggle! box)
    ))

(defn mount!
  []
  (doseq [t ($$ ".collapse-title")] (events/listen t "click" click-handler))

  ;; If ?season=2023-2024 is present, open that collapse
  (when-let [season (.get (js/URLSearchParams. (.-search js/location)) "season")]
    (when-let [el (.querySelector js/document
                                (str ".collapse[gallery-season-key=\"" season "\"]"))]
    (open! el))))

(defn ^:export init []
  (mount!))

(init)
