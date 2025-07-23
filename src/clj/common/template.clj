(ns common.template
  (:require
   [hiccup.page :refer [html5 include-css]]
   [utils.config :refer [config]]))

(defn head
  "Head of the page, with title, descriptions and optional extra-elements"
  [{:keys [title description extra-elements]
    :or   {extra-elements []}}]
    [:head
     [:meta {:charset "UTF-8"}]
     [:base {:href (:base config)}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:title title]
     (when description
       [:meta {:name "description" :content description}])
     [:link {:rel  "icon"
             :type "image/svg+xml"
             :href "assets/brother-favicon.svg"}]
     (include-css "css/style.css")
     [:script {:src "//gc.zgo.at/count.js"
               :data-goatcounter (:goat-counter-url config)
               :async true}]
     (for [p extra-elements] p)])

(defn header
  "Website name with link to root page"
  []
  [:header.text-center.my-8
   [:a.text-myflame.text-3xl.font-bold
    {:class "decoration-myflame underline-offset-4 hover:text-myflame/80 select-none"
     :href "./"}
    "🏒 BigV Webpage 🏒"]])

(defn layout
  "Main layout of the app - evyerything should inherit from here"
  [{:keys [title description extra-elements]} & content]
  (html5
   (head {:title       title
          :description description
          :extra-elements    extra-elements})
   [:body
    [:div#root
     (header)
     content]]))
