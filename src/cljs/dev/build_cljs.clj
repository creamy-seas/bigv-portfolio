(ns dev.build-cljs
  (:require [cljs.build.api :as cljs]))

(def bundles
  ;; Define all the namespaces to builds into js files here
  ;; e.g. :example 'gallery-build tells to evaluate the gallery.build and put it into example.js
  {:gallery 'gallery.build
   :landing 'landing.build})

(def paths
  {:src-dirs "src/cljs"
   :out-dirs {:advanced "target/cljs"
              :simple  "resources/public/js"}})

(defn out-dir [optimizations]
  (get-in paths [:out-dirs optimizations]))

(defn output-to
  "Output files based off the bundle name"
  [bundle optimizations]
  (str (out-dir optimizations) "/" (name bundle) ".js"))

(defn modules-map
  "Make a module for each of the bundles, specifing namespace to evaluate
  and where to write the output"
  [optimizations]
  (into {}
        (map (fn [[bundle entry-ns]]
               [(keyword (name bundle))
                {:entries [entry-ns]
                 :output-to (output-to bundle optimizations)}])
             bundles)))

(defn build-config
  [optimizations]
  {:optimizations   optimizations
   :modules         (modules-map optimizations)
   :output-dir      (out-dir optimizations)
   :parallel-build  true
   :target          :none
   :verbose         true
   :source-map      (not= optimizations :advanced)})

(defn -main
  "Pass in `prod` or `dev` build as argument"
  [& [mode]]
  (let [optimization  (if (= mode "prod") :advanced :simple)
        cfg    (build-config optimization)
        action (if (= optimization :advanced) cljs/build cljs/watch)]
    (println "▶ cljs compiling" optimization)
    (action (:src-dirs paths) cfg)))
