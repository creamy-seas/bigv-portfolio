(ns gallery.modal
  (:require [goog.events :as events]))

(defonce ^:private modal-state (atom nil))

(defn set-gallery-iframe
  "The iframe is populated with supplied values"
  [src description date]
  (let [iframe (.getElementById js/document "gallery-modal-iframe")]
    (set! (.-src iframe) src)
    (set! (.-title iframe) description)
    (set! (.-textContent (.getElementById js/document "gallery-modal-description"))
          description)
    (when date
      (set! (.-textContent (.getElementById js/document "gallery-modal-date"))
            (.toLocaleDateString (js/Date. date)))))

  )
(defn display-gallery-modal
  "Populate modal with information for item gallery-idx in gallery"
  [gallery-idx]
  (when-not (= gallery-idx @modal-state)
    (let [data (.-GALLERY_DATA js/window)
          item (aget data gallery-idx)]
      (reset! modal-state gallery-idx)
      (set-gallery-iframe (.-src item) (.-description item) (.-date item))
      (.remove (.-classList (.getElementById js/document "gallery-modal")) "hidden"))))

(defn close-gallery-modal [event]
  (.stopPropagation event)
  (when-let [el (.getElementById js/document "gallery-modal")]
    (.add (.-classList el) "hidden")
    (reset! modal-state nil)
    (set-gallery-iframe nil nil nil)))

(defn open-gallery-modal [event]
  (.stopPropagation event)
  (display-gallery-modal (js/parseInt (.getAttribute (.-currentTarget event) "gallery-idx"))))

(defn show-future [event]
  (.stopPropagation event)
  (display-gallery-modal (max 0 (- @modal-state 1))))

(defn show-past [event]
  (.stopPropagation event)
  (display-gallery-modal (min
                    (.-GALLERY_DATA_MAX_IDX js/window)
                    (+ @modal-state 1))))

(defn mount! []
  (when-let [el (.getElementById js/document "gallery-modal")]
    (events/listen el "click" close-gallery-modal))
  (when-let [el (.getElementById js/document "gallery-modal-future")]
    (events/listen el "click" show-future))
  (when-let [el (.getElementById js/document "gallery-modal-past")]
    (events/listen el "click" show-past))
  ;; (when-let [outer-el (.getElementById js/document "gallery-grid")]
  ;;   (events/listen outer-el "click"
  ;;                  (fn [e]
  ;;                    (let [el (.-target e)]
  ;;                      (when (.matches el ".gallery-card")
  ;;                        (open-gallery-modal e))))))
  (doseq [el (array-seq (.querySelectorAll js/document ".gallery-card"))]
    (events/listen el "click" open-gallery-modal))
  )

(defn ^:export init []
  (mount!))

(init)
