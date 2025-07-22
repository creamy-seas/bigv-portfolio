(ns dev.build
  (:require [cljs.build.api :as cljs]
            [clojure.java.io  :as io]))

(def bundles
  {:gallery ["gallery.collapse" "gallery.modal"]
   :landing ["landing.game-graph"]})

(def common-opts
  {:output-dir      "target/cljs/out"
   :parallel-build  true
   :target          :none
   :verbose         true})

(defn output-to [bundle-name optimizations]
  (if (= optimizations :advanced)
    (str "target/cljs/" bundle-name "_to-bundle.js")
    (str "resources/public/js/" bundle-name ".js")))

(defn make-module
  "Single module that contains all the sources, writting them to output-to"
  [bundle-name ns-list optimizations]
  (let [output-to (output-to bundle-name optimizations)]
    (io/make-parents output-to)
    {:entries   (set (map symbol ns-list))
     :output-to output-to}))

(defn modules-map [optimizations]
  (into {}
        (for [[k ns-list] bundles]
          [(keyword (name k))
           (make-module (name k) ns-list optimizations)])))

(defn -main
  "Pass in `prod` or `dev` build as argument"
  [& profile]
  (let [{:keys [optimizations source-map watch] :as opts}
        (case profile
          "prod"
          {:optimizations   :advanced
           :source-map      false
           :watch           false}
          {:optimizations   :simple
           :source-map      true
           :watch           true})]
    (println "-> Building (" optimizations
             (if watch "with watch" "one-shot")
             (if source-map "with source maps" "")
             ")")
    (let [cfg (merge
               common-opts
               opts
               {:modules (modules-map optimizations)})]
      (if watch
        (cljs/watch "src/cljs" cfg)
        (cljs/build "src/cljs" cfg)))))
