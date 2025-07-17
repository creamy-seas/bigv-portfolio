(ns gallery.modal
  (:require [goog.events :as events]))

(defonce ^:private modal-state (atom nil))

(defn display-modal
  "Populate modal with information for item gallery-idx in gallery"
  [gallery-idx]
  (when-not (= gallery-idx @modal-state)
    (let [data (.-GALLERY_DATA js/window)
          iframe (.getElementById js/document "gallery-modal-iframe")
          item (aget data gallery-idx)]
      (reset! modal-state gallery-idx)
      ;; Remember, .-src is objects accessing
      ;; And .remove is function
      (set! (.-src iframe) (.-iframe item))
      (set! (.-title iframe) (.-description item))
      (set! (.-textContent (.getElementById js/document "gallery-modal-description"))
            (.-description item))
      (set! (.-textContent (.getElementById js/document "gallery-modal-date"))
            (.toLocaleDateString (js/Date. (.-date item))))
      (.remove (.-classList (.getElementById js/document "gallery-modal")) "hidden"))))

(defn close-modal [event]
  (.stopPropagation event)
  (when-let [el (.getElementById js/document "gallery-modal")]
    (.add (.-classList el) "hidden")
    (reset! modal-state nil)))

(defn open-modal [event]
  (.stopPropagation event)
  (display-modal (.getAttribute (.-currentTarget event) "gallery-idx")))

(defn show-prev [event]
  (.stopPropagation event)
  (when-let [gallery-idx @modal-state]
    (display-modal (max 0 (- gallery-idx 1)))))

(defn show-next [event]
  (.stopPropagation event)
  (when-let [gallery-idx @modal-state]
    (display-modal (min
                    (.-GALLERY_DATA_MAX_IDX js/window)
                    (+ gallery-idx 1)))))

(defn mount! []
  (when-let [el (.getElementById js/document "gallery-modal")]
    (events/listen el "click" close-modal))
  (when-let [el (.getElementById js/document "gallery-modal-prev")]
    (events/listen el "click" show-prev))
  (when-let [el (.getElementById js/document "gallery-modal-next")]
    (events/listen el "click" show-next))
  ;; (when-let [outer-el (.getElementById js/document "gallery-grid")]
  ;;   (events/listen outer-el "click"
  ;;                  (fn [e]
  ;;                    (let [el (.-target e)]
  ;;                      (when (.matches el ".gallery-card")
  ;;                        (open-modal e))))))
  (doseq [el (array-seq (.querySelectorAll js/document ".gallery-card"))]
    (events/listen el "click" open-modal))
  )

(defn ^:export init []
  (mount!))

(init)
