(ns pages.landing.game-graph
  (:require [common.elements]))

(defn get-cumulative-stats [game-stats]
  (letfn [(accumulate [remaining result running]
            (if (empty? remaining)
              result
              (let [current-stats (first remaining)
                    new {:gameNumber (:gameNumber current-stats)
                         :goals (+ (:goals running) (:goals current-stats))
                         :passes (+ (:passes running) (:passes current-stats))
                         :shots (+ (:shots running) (:shots current-stats))
                         :carries (+ (:carries running) (:carries current-stats))
                         :takeaways (+ (:takeaways running) (:takeaways current-stats))}
                    new-cum-list (conj result new)]
                (recur (rest remaining) new-cum-list new))))]
    (accumulate game-stats [] {:goals 0 :passes 0 :shots 0 :carries 0 :takeaways 0})))

(defn render
  []
  [:div.p-2.max-w-4xl.mx-auto
   (common.elements/fat-title "📈 Game Stats")
   [:canvas#game-graph.w-full {:class "max-h-[800px]"}]
   [:div.flex.justify-center.mt-4
    [:button#toggle-game-graph {:class "px-4 py-2 bg-myflame text-bg rounded hover:bg-fg transition"}
     "Show cumulative"]]])
