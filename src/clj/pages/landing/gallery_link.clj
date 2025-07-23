(ns pages.landing.gallery-link)

(defn render []
  [:section.text-center.p-6 [:a {:href "gallery"
                         :class "
          text-myflame text-3xl font-bold
          underline decoration-myflame underline-offset-4
          hover:text-myflame/80
        "}
                     "View Gallery"]])
