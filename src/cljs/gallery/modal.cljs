(ns gallery.modal
  (:require [utils.dom-operations :as dom]
            [gallery.collapse     :as collapse]))

(defonce
  ;; Keep the gallery-idx that should be displayed or `nil` if closed
  modal-state (atom nil))

(defonce max-gallery-idx*
  (aget js/window "GALLERY_DATA_MAX_IDX"))

(defn set-gallery-modal-media
  "Populate the modal with supplied media and metadata."
  [src type description date age]
  (let [image (dom/get-element-by-id "gallery-modal-image")
        video (dom/get-element-by-id "gallery-modal-video")
        date-opts #js {"day"   "numeric"
                       "month" "long"
                       "year"  "numeric"}
        locale (aget js/navigator "language")
        date-str (str (.toLocaleDateString
                       (js/Date. date)
                       locale
                       date-opts)
                      " (" age ")")]

    (if (= type "image")
      (do
        (.pause video)
        (set! (.-src video) "")
        (.add (.-classList video) "hidden")

        (set! (.-src image) src)
        (set! (.-alt image) description)
        (.remove (.-classList image) "hidden"))

      (do
        (set! (.-src image) "")
        (.add (.-classList image) "hidden")
        (.play video)

        (set! (.-src video) src)
        (.remove (.-classList video) "hidden")))

    (set! (.-textContent
           (dom/get-element-by-id "gallery-modal-description"))
          description)

    (set! (.-textContent
           (dom/get-element-by-id "gallery-modal-date"))
          date-str)))

(defn clear-gallery-modal-media []
  (let [image (dom/get-element-by-id "gallery-modal-image")
        video (dom/get-element-by-id "gallery-modal-video")]
    (.pause video)
    (set! (.-src video) "")
    (set! (.-src image) "")))

(defn sync-collapse
  "Ensures that item in view has its collapse box expanded."
  [item-season]
  (doseq [box (dom/get-all ".collapse[gallery-season-key]")]
    (if (= (.getAttribute box "gallery-season-key") item-season)
      (collapse/open! box)
      (collapse/close! box))))

(defn display-gallery-modal
  "Populate modal with information for item gallery-idx in gallery."
  [gallery-idx]
  (when-not (= gallery-idx @modal-state)
    (let [data (aget js/window "GALLERY_DATA")
          item (aget data gallery-idx)]
      (reset! modal-state gallery-idx)

      ;; Do not touch these - I tried to do aget and they failed,
      ;; but for data the .-PROP does not work. Mysterious.
      (set-gallery-modal-media
       (.-src item)
       (aget item "type")
       (.-description item)
       (aget item "date")
       (aget item "age"))

      (.remove
       (.-classList (dom/get-element-by-id "gallery-modal"))
       "hidden")

      (sync-collapse (aget item "season")))))

(defn close-gallery-modal [event]
  (.stopPropagation event)
  (when-let [el (dom/get-element-by-id "gallery-modal")]
    (.add (.-classList el) "hidden")
    (reset! modal-state nil)
    (clear-gallery-modal-media)))

(defn open-gallery-modal [event]
  (.stopPropagation event)
  (display-gallery-modal
   (js/parseInt
    (.getAttribute (.-currentTarget event) "gallery-idx"))))

(defn show-past [event]
  (.stopPropagation event)
  (display-gallery-modal
   (max 0 (- @modal-state 1))))

(defn show-future [event]
  (.stopPropagation event)
  (display-gallery-modal
   (min max-gallery-idx*
        (+ @modal-state 1))))

(defn handle-keydown [event]
  (when (some? @modal-state)
    (case (.-key event)
      "ArrowLeft"  (show-future event)
      "ArrowRight" (show-past event)
      "Escape"     (close-gallery-modal event)
      nil)))

(defn on-load
  "If ?gallery-idx=123 is present in url args, open the modal."
  []
  (when-let [gallery-idx
             (.get
              (js/URLSearchParams. (.-search js/location))
              "gallery-idx")]
    (display-gallery-modal gallery-idx)))

(defn ^:export init []
  (dom/add-click-listener-by-id
   "gallery-modal"
   close-gallery-modal)

  (dom/add-click-listener-by-id
   "gallery-modal-future"
   show-future)

  (dom/add-click-listener-by-id
   "gallery-modal-past"
   show-past)

  (doseq [el (dom/get-all ".gallery-card")]
    (dom/add-listener el "click" open-gallery-modal))

  (dom/add-listener js/document "keydown" handle-keydown)

  (on-load))

(init)
