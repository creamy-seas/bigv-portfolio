(ns common.metadata)

(defn head []
  ;; Definition of site-wide info
  [:head
   [:meta {:charset "UTF-8"}]
   [:link {:rel "icon" :type "image/svg+xml" :href "/assets/brother-favicon.svg"}]
   ;;[:link {:rel "preload" :as "image" :href "/assets/profile.avif" :type "image/avif" :fetchpriority "high"}]
   [:link {:href "css/style.css" :rel "stylesheet"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
   [:title "BigV Webpage"]])
