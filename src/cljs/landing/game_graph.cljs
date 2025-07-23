(ns landing.game-graph
  ;;(:require [goog.events :as events])
  )
(defn dataset
  [game-stats label k color]
  {:label label
   :data  (mapv k game-stats)
   ;; line -------------
   :borderColor color
   ;; points -------------
   :pointBorderColor     color
   :pointHoverBackgroundColor color})

(defn make-datasets [game-stats]
  [(dataset game-stats "Goals"     :goals     "#eec900")
   (dataset game-stats "Shots"     :shots     "#ff7300")
   (dataset game-stats "Passes"    :passes    "#00bfff")
   (dataset game-stats "Carries"   :carries   "#82ca9d")
   (dataset game-stats "Takeaways" :takeaways "#ff1493")])

(defonce view-mode* (atom :cumulative))

(defonce per-game-stats*
  (js->clj (aget js/window "GAME_STATS_DATA")  :keywordize-keys true))

(defonce cumulative-game-stats*
  (js->clj (aget js/window "CUMULATIVE_GAME_STATS_DATA") :keywordize-keys true))

(defn current-stats
  "Get the stats depending on the view mode"
  []
  (if (= @view-mode* :cumulative)
    cumulative-game-stats*
    per-game-stats*))

(def chart-opts
  {:responsive true
   :maintainAspectRatio false
   ;;:aspectRatio 1
   :interaction {:mode "index" :axis "x" :intersect true}

   ;; Common point params - line spefic ones passed in with datased
   :elements
   {:line {:tension              0.2
           :fill                 false}
    :point {:pointBackgroundColor "#ffffff"
            :pointBorderWidth     2
            :pointRadius          3

            :pointHoverBorderColor     "#ffffff"
            :pointHoverBorderWidth     3
            :pointHoverRadius     8}}

   :plugins
   {:legend {:position "top"
             :labels {:font {:size 20}
                      :boxHeight 1
                      :boxWidth 20}}
    :tooltip {:boxHeight 1
              :boxWidth 15
              :boxPadding 10
              :bodyFont  {:size 18}
              :callbacks
              {:title
               (fn [items]
                 (when-let [item (aget items 0)]
                   (let [idx   (js/parseInt (aget item "dataIndex"))
                         game  (nth (current-stats) idx)
                         date  (:date       game)
                         loc   (:location   game)
                         name  (:name       game)]
                     (str date ": @" loc "\n" name))))}}}

   :scales
   {:x {:grid {:color "rgba(128,128,128,0.20)" :lineWidth 0.5}
        :border {:display true :width 2 :color "#696969"}
        :ticks  {:font {:size 18}}
        :title {:display true :text "Game #" :font {:size 20}}}
    :y {:ticks  {:font {:size 18}}
        :grid {:color "rgba(128,128,128,0.20)" :lineWidth 0.5}
        :border {:display true :width 2 :color "#696969"}
        :title {:display true :text "Count"  :font {:size 20}}}}})

(defn toggle-view!
  "Flip the view-mode and update the chart"
  [^js chart]
  (swap! view-mode* (fn [m] (if (= m :cumulative) :per-game :cumulative)))
  (let [stats (current-stats)]
    (aset (aget chart "data") "labels" (clj->js (mapv :gameNumber stats)))
    (aset (aget chart "data") "datasets" (clj->js (make-datasets stats)))
    (.call (aget chart "update") chart)
    (.update chart))
  (let [btn (.getElementById js/document "toggle-game-graph")
        new-label (if (= @view-mode* :cumulative)
                    "Show per-game"
                    "Show cumulative")]
    (set! (.-textContent btn) new-label)))

(defn init-graph! []
  (let [ctx (.getContext (.getElementById js/document "game-graph") "2d")
        cfg (clj->js {:type "line"
                      :data {:labels   (mapv :gameNumber cumulative-game-stats*)
                             :datasets (make-datasets cumulative-game-stats*)}
                      :options chart-opts})]
    (js/Chart. ctx cfg)))

(defn ^:export init
  "Populate graph and hook up listeners"
  []
  (let [chart   (init-graph!)]
    (when-let [btn (.getElementById js/document "toggle-game-graph")]
      (.addEventListener btn "click" #(toggle-view! chart)))))

(init)
