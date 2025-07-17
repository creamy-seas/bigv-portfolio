(ns common.layout)

(defn layout
  "Common page layout applied to all pages"
  [content]
  [:body
   [:div#root
    [:header.text-center.my-8
     [:a
      {:class "
            text-myflame text-3xl font-bold
            decoration-myflame underline-offset-4
            hover:text-myflame/80 select-none
          "
      } "🏒 BigV Webpage 🏒"]]
    content]])
