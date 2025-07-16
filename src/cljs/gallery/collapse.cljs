(ns gallery.collapse
  (:require [goog.events :as events]))

(js/console.log "🟢 collapse.cljs loaded")

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
  (js/console.log "🔧 mount! running…")
  ;; Attach the click handler to each title
  (doseq [t ($$ ".collapse-title")] (events/listen t "click" click-handler))

  ;; If ?season=x is present, open that season once
  (let [p (js/URLSearchParams. (.-search js/location))
        s (.get p "season")]
    (when-let [target (when s
                        (.querySelector js/document
                                        (str ".collapse[data-season=\"" s "\"]")))]
      (open! target))))

(defn ^:export init []
  (js/console.log "🚀 init called")
  (mount!))

(init)
