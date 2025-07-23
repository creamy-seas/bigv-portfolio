(ns gallery.modal)

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
            (.toLocaleDateString (js/Date. date) "en" #js {:day "numeric" :month "long" :year "numeric"})))))

(defn display-gallery-modal
  "Populate modal with information for item gallery-idx in gallery"
  [gallery-idx]
  (when-not (= gallery-idx @modal-state)
    (let [data (aget js/window "GALLERY_DATA")
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

(defonce max-gallery-idx*
  (aget js/window "GALLERY_DATA_MAX_IDX"))

(defn show-past [event]
  (.stopPropagation event)
  (display-gallery-modal (min
                          max-gallery-idx*
                          (+ @modal-state 1))))

(defn click-listener
  "Hook up a listenter to the DOM element found by id with the provided function"
  [element-id callback]
  (when-let [el (.getElementById js/document element-id)]
    (.addEventListener el "click" callback)))

(defn mount! []
  (click-listener "gallery-modal" close-gallery-modal)
  (click-listener "gallery-modal-future" show-future)
  (click-listener "gallery-modal-past" show-past)
  (doseq [el (array-seq (.querySelectorAll js/document ".gallery-card"))]
    (.addEventListener el "click" open-gallery-modal)))

(defn ^:export init []
  (mount!))

(init)
