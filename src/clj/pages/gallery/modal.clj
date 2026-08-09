(ns pages.gallery.modal)

(def nav-arrow-class
  ;; Very tedious css class to ensure nice clicky navigation arrows
  "flex-1 h-8
   flex items-center justify-center
   bg-fg/20 text-bg/60 ring-mytheme/20 ring-2
   hover:bg-mytheme hover:text-bg
   active:bg-mytheme active:text-bg
   transition-colors duration-200
   rounded-lg")

(defn render
  "A popup with a large display of gallery media."
  []
  [:div#gallery-modal.select-none.js-only
   {:class "fixed flex hidden
            items-center justify-center
            p-0 sm:p-4
            bg-black/75 inset-0 z-50"}

   [:div.modal-content
    {:class "bg-bg
             p-3 sm:p-6
             rounded-lg
             w-full lg:max-w-[70vw]
             max-h-screen overflow-auto"
     :onclick "event.stopPropagation()"}

    [:div.w-full.aspect-video.overflow-hidden
     [:img#gallery-modal-image.hidden.w-full.h-full.object-contain
      {:src ""
       :alt ""}]

     [:video#gallery-modal-video.hidden.w-full.h-full.object-contain
      {:src ""
       :controls true
       :autoplay true
       :playsinline true}]]

    [:div.flex.gap-4.mt-3
     [:button#gallery-modal-future
      {:class nav-arrow-class
       :aria-label "Show newer gallery item"}
      [:span.text-3xl "‹"]]

     [:button#gallery-modal-past
      {:class nav-arrow-class
       :aria-label "Show older gallery item"}
      [:span.text-3xl "›"]]]

    [:h3#gallery-modal-description.text-xl.font-semibold.text-mytheme.mt-4 ""]

    [:p#gallery-modal-date.text-sm.text-gray-400 ""]]])
