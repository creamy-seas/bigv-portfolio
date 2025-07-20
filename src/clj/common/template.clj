(ns common.template
  (:require
   [hiccup.page :refer [html5 include-css]]))

(defn head
  "Head of the page, with title, descriptions and optional preloads"
  [{:keys [title description preloads]
    :or   {preloads []}}]
  [:head
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
   [:title title]
   (when description
     [:meta {:name "description" :content description}])
   [:link {:rel  "icon"
           :type "image/svg+xml"
           :href "/assets/brother-favicon.svg"}]
   ;; TODO: remove with normal ref
   [:script {:src "https://cdn.jsdelivr.net/npm/chart.js@4.5.0/dist/chart.umd.min.js"}]
   (include-css "/css/style.css")
   (for [p preloads] p)])

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
  [{:keys [title description preloads]} & content]
  (html5
   (head {:title       title
          :description description
          :preloads    preloads})
   [:body
    [:div#root
     (header)
     content]]))
