(ns landing.game-graph
  (:require [goog.events :as events]))

(def games
  [{:gameNumber 1  :goals 0 :passes 0  :shots  1  :carries  3  :takeaways  0}
   {:gameNumber 2  :goals 0 :passes 0  :shots  2  :carries  5  :takeaways  1}
   {:gameNumber 3  :goals 0 :passes 0  :shots  5  :carries  5  :takeaways  5}
   {:gameNumber 4  :goals 0 :passes 0  :shots 11  :carries 11  :takeaways 10}
   {:gameNumber 5  :goals 0 :passes 0  :shots 12  :carries 13  :takeaways 12}
   {:gameNumber 6  :goals 0 :passes 1  :shots 12  :carries 16  :takeaways 16}
   {:gameNumber 7  :goals 0 :passes 5  :shots 15  :carries 16  :takeaways 27}
   {:gameNumber 8  :goals 0 :passes 6  :shots 16  :carries 18  :takeaways 33}
   {:gameNumber 9  :goals 0 :passes 6  :shots 16  :carries 18  :takeaways 39}
   {:gameNumber 10 :goals 0 :passes 9  :shots 18  :carries 21  :takeaways 44}
   {:gameNumber 11 :goals 0 :passes 11 :shots 20  :carries 22  :takeaways 48}
   {:gameNumber 12 :goals 1 :passes 11 :shots 22  :carries 26  :takeaways 50}
   {:gameNumber 13 :goals 1 :passes 13 :shots 24  :carries 31  :takeaways 55}
   {:gameNumber 14 :goals 1 :passes 15 :shots 24  :carries 35  :takeaways 64}
   {:gameNumber 15 :goals 2 :passes 18 :shots 26  :carries 46  :takeaways 69}
   {:gameNumber 16 :goals 3 :passes 20 :shots 30  :carries 53  :takeaways 70}])


(defn dataset
  [label k color]
  {:label label
   :data  (mapv k games)

   ;; line
   :borderColor          color
   :tension              0.3      ;; keep your existing settings
   :fill                 false

   ;; hover

   ;; points -------------
   :pointBackgroundColor "#ffffff"
   :pointBorderColor     color
   :pointBorderWidth     2
   :pointRadius          3

   :pointHoverBackgroundColor color
   :pointHoverBorderColor     "#ffffff"
   :pointHoverBorderWidth     3
   :pointHoverRadius     4
   })

(defn make-datasets []
  [(dataset "Goals"     :goals     "#eec900")
   (dataset "Shots"     :shots     "#ff7300")
   (dataset "Passes"    :passes    "#00bfff")
   (dataset "Carries"   :carries   "#82ca9d")
   (dataset "Takeaways" :takeaways "#ff1493")])

(def chart-opts
  ;; Clojure map ­→ later fed to `clj->js`
  {:responsive true

   :interaction {:mode "index" :axis "x" :intersect false}



   :plugins
   {
    :legend {:position "top"
             :labels {:font {:size 20}
                      :boxHeight 1
                      :boxWidth 20}}
    :tooltip {:boxHeight 1
              :boxWidth 15
              :boxPadding 10
              :bodyFont  {:size 20}
              :callbacks
              {:title
               (fn [ctx] ;; ctx is an array of TooltipItem; take the first one
                 (when-let [item (aget ctx 0)]
                   (str "Game #" (.-label item))))}}}

   :scales
   {:x {:grid {:color "rgba(128,128,128,0.20)" :lineWidth 0.5}
        :border {:display true :width 2 :color "#696969"}
        :ticks  {:font {:size 20}}
        :title {:display true :text "Game #" :font {:size 24}}}
    :y {:ticks  {:font {:size 20}}
        :grid {:color "rgba(128,128,128,0.20)" :lineWidth 0.5}
        :border {:display true :width 2 :color "#696969"}
        :title {:display true :text "Count"  :font {:size 24}}}}})


(defonce chart* (atom nil))

(defn init-graph! []
  (let [ctx (.getContext (.getElementById js/document "game-graph") "2d")
        cfg (clj->js {:type "line"
                      :data {:labels   (mapv :gameNumber games)
                             :datasets (make-datasets)}
                      :options chart-opts})]
    (reset! chart* (js/Chart. ctx cfg))
    ))


(defn mount!
  "Populate graph and hook up listeners"
  []
  (init-graph!)
  ;; (when-let [el (.getElementById js/document "toggle-game-graph")]
  ;;   (events/listen  "click" toggle-view))
  )

(defn ^:export init []
  (mount!))

(init)


;; (defonce state (atom {:mode :per-game
;;                       :graph nil}))

;;(defn toggle-view! []
  ;; (swap! state update :mode
  ;;        (fn [m] (if (= m :per-game) :cumulative :per-game)))
  ;; (let [{:keys [mode graph]} @state
  ;;       new-data (make-datasets mode)]
  ;;   (aset graph "data" "datasets" (clj->js new-data))
  ;;   (aset graph "data" "labels"  (clj->js (mapv :gameNumber games)))
  ;;   (.update graph)
  ;;   ;; update button text
  ;;   (set! (.-innerText (gdom/getElement "toggle-view"))
  ;;         (if (= mode :per-game) "Show Cumulative" "Show Per-Game")))
;;  )
