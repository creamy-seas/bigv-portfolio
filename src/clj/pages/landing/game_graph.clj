(ns pages.landing.game-graph
  (:require [common.elements]))

(defn render []
  [:section.max-w-4xl.mx-auto
   (common.elements/fat-title "📈 Game Stats")
   [:div {:class "max-h-[1000px] min-h-[500px] relative"}
    [:canvas#game-graph.w-full]]
   [:div.flex.justify-center.mt-4
    [:button#toggle-game-graph {:class "px-4 py-2 bg-myflame text-bg rounded hover:bg-fg transition"}
     "Show per-game"]]])
