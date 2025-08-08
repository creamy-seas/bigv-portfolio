(ns pages.landing.gallery-link
  (:require [utils.url :refer [put-on-base]]))

(defn render
  "Link to gallery page - nice, fat, and center of page"
  []
  [:section.text-center.p-6 [:a {:href (put-on-base "/gallery")
                                 :class "text-myflame text-3xl font-bold
                                         underline underline-offset-4
                                         hover:text-myflame/80"}
                             "View Gallery"]])
