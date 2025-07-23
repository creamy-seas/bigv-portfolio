(ns pages.error.page
  (:require
   [utils.url    :refer [put-on-base]]
   [common.template :refer [layout]]
   [common.elements]))

(defn render
  "404 with a funny dude and redirect"
  []
  (layout
   {:title       "BigV - 404 Page not found"
    :description "Nothing here!"
    ;; :extra-elements [[:meta {:http-equiv "refresh"
    ;;                          :content (str "10; url=" (:base config))
    ;;                          :charset "UTF-8"}]]
    }
   [:container.space-y-8.text-center.flex.flex-col.justify-center
    [:div.relative.flex.items-center.justify-center
     [:div
      {:class "absolute inset-0
               rounded-lg
               bg-gradient-to-br from-myflame via-orange-300/60 to-transparent
               filter blur-2xl"}]
     [:img
      {:src   (put-on-base "/assets/brother-favicon.svg")
       :alt   "Let's go!"
       :class "relative z-10
               w-[40%] w-min-[200px] h-auto mb-4"}]]
    (common.elements/fat-title "404: Sorry, nothing here")]))
