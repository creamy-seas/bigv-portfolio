(ns dev.build-cljs
  (:require [cljs.build.api :as cljs]))

(def bundles
  ;; Define all the namespaces to builds into js files here
  ;; e.g. :example 'gallery-build tells to evaluate the gallery.build and put it into example.js
  {:gallery 'gallery.build
   :landing 'landing.build})

(def base-opts
  {:src-dirs        "src/cljs"
   :output-dir      "resources/public/js"
   :parallel-build  true
   :target          :none
   :verbose         true})

(defn output-to
  "Output files based off the bundle name"
  [bundle optimizations]
  (let [bundle-name (name bundle)]
    (if (= optimizations :advanced)
      (str "target/cljs/" bundle-name "_to-bundle.js")
      (str "resources/public/js/" bundle-name ".js"))))

(defn modules-map
  "Make a module for each of the bundles, specifing namespace to evaluate
  and where to write the output"
  [optimizations]
  (into {}
        (map (fn [[bundle entry-ns]]
               [(keyword (name bundle))
                {:entries #{entry-ns}
                 :output-to (output-to bundle optimizations)}]) bundles)))

(defn build-config
  "Return the full cljs compiler map"
  [mode]
  (let [opts (if (= mode "prod")
               {:optimizations   :advanced
                :source-map      false
                :watch           false}
               {:optimizations   :simple
                :source-map      true
                :watch           true})]
    (merge base-opts opts {:modules (modules-map (:optimizations opts))})))

(defn -main
  "Pass in `prod` or `dev` build as argument"
  [& [mode]]
  ;; (cljs/watch (:src-dirs base-opts) cfg)
  (let [cfg (build-config mode)
        action (if (:watch cfg) cljs/watch cljs/build)]
    (println "▶ cljs compiling" (:optimizations cfg)
             (if (:watch cfg) "(watching)" "(building)"))
    (action (:src-dirs base-opts) cfg)))
