(ns gallery.modal
  (:require [utils.dom-operations :refer [get-element-by-id
                                          get-all
                                          add-listener
                                          add-click-listener-by-id]]))

(defonce
  ;; Keep the gallery-idx that should be displayed or `nil` if closed"
  modal-state (atom nil))

(defonce max-gallery-idx*
  (aget js/window "GALLERY_DATA_MAX_IDX"))

(defn set-gallery-modal-iframe
  "The iframe is populated with supplied values"
  [src description date]
  (let [iframe (get-element-by-id "gallery-modal-iframe")
        date-opts #js {"day"   "numeric"
                       "month" "long"
                       "year"  "numeric"}
        locale (aget js/navigator "language")
        date-str (.toLocaleDateString (js/Date. date) locale date-opts)]
    (set! (.-src iframe) src)
    (set! (.-title iframe) description)
    (set! (.-textContent (get-element-by-id "gallery-modal-description")) description)
    (set! (.-textContent (get-element-by-id "gallery-modal-date")) date-str)))

(defn display-gallery-modal
  "Populate modal with information for item gallery-idx in gallery"
  [gallery-idx]
  (when-not (= gallery-idx @modal-state)
    (let [data (aget js/window "GALLERY_DATA")
          item (aget data gallery-idx)]
      (reset! modal-state gallery-idx)
      ;; Do not touch these - I tried to do aget and they failed, but for data the .-PROP does not work
      ;; Mysterious
      (set-gallery-modal-iframe
       (.-src item) (.-description item) (aget item "date"))
      (.remove (.-classList (get-element-by-id "gallery-modal")) "hidden"))))

(defn close-gallery-modal [event]
  (.stopPropagation event)
  (when-let [el (get-element-by-id "gallery-modal")]
    (.add (.-classList el) "hidden")
    (reset! modal-state nil)
    (set-gallery-modal-iframe nil nil nil)))

(defn open-gallery-modal [event]
  (.stopPropagation event)
  (display-gallery-modal
   (js/parseInt (.getAttribute (.-currentTarget event) "gallery-idx"))))

(defn show-future [event]
  (.stopPropagation event)
  (display-gallery-modal
   (max 0 (- @modal-state 1))))

(defn show-past [event]
  (.stopPropagation event)
  (display-gallery-modal (min
                          max-gallery-idx*
                          (+ @modal-state 1))))

(defn handle-keydown [event]
  (when (some? @modal-state)
    (case (.-key event)
      "ArrowLeft"  (show-future event)
      "ArrowRight" (show-past event)
      "Escape" (close-gallery-modal event)
      nil)))

(defn ^:export init []
  (add-click-listener-by-id "gallery-modal" close-gallery-modal)
  (add-click-listener-by-id "gallery-modal-future" show-future)
  (add-click-listener-by-id "gallery-modal-past" show-past)
  (doseq [el (get-all ".gallery-card")]
    (add-listener el "click" open-gallery-modal))
  (add-listener js/document "keydown" handle-keydown))

(init)
