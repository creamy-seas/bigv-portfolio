(ns dev.build-cljs
  (:require [cljs.build.api :as cljs]))

(def bundles
  ;; Define all the namespaces to builds into js files here
  ;; e.g. :example 'gallery-build tells to evaluate the gallery.build and put it into example.js
  {:gallery 'gallery.build
   :landing 'landing.build})

(def paths
  ;; For production take artifacts out of resources/public/js
  {:src-dirs "src/cljs"
   :out-dirs {:advanced "target/cljs"
              :simple  "resources/public/js"}})

(defn out-dir [optimizations]
  (get-in paths [:out-dirs optimizations]))

(defn modules-map
  "Make a module for each of the bundles, specifing namespace to evaluate
  and where to write the output"
  [optimizations]
  (merge (into {}
        (map (fn [[bundle entry-ns]]
               [(keyword (name bundle))
                {:entries #{entry-ns}
                 :output-to (str "resources/public/js/" (name bundle) ".js")}])
             bundles))
         {:cljs-base {:output-to "resources/public/js/cljs_base.js"}}
         ))

(defn build-config
  [optimizations]
  {:optimizations   optimizations
   :modules         (modules-map optimizations)
   :output-dir      (out-dir optimizations)
   :parallel-build  true
   :closure-defines {"goog.DEBUG" false}
   :verbose         true})

(defn -main
  "Pass in `prod` or `dev` build as argument"
  [& [mode]]
  (let [optimization  (if (= mode "prod") :advanced :simple)
        cfg    (build-config optimization)
        action (if (= optimization :advanced) cljs/build cljs/watch)]
    (println "▶ cljs compiling" optimization cfg)
    (action (:src-dirs paths) cfg)))
