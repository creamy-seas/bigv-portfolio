(ns dev.build-clj
  (:require [pages.gallery.page :as gallery]
            [pages.landing.page :as landing]
            [clojure.java.io   :as io]))

(defn- ensure-parent!
  "Make sure the parent directory of file-path exists."
  [^String file-path]
  (let [parent (.getParentFile (io/file file-path))]
    (when-not (.exists parent)
      (.mkdirs parent))))

(def pages
  {"resources/public/index.html"         landing/page
   "resources/public/gallery/index.html" gallery/page})

(defn build-all!
  []
  (doseq [[out-path render-fn] pages]
    (ensure-parent! out-path)
    (spit out-path (render-fn))
    (println "✔" out-path "generated")))

(defn -main
  "Build all static pages."
  [& _]
  (println "▶ clj compiling")
  (build-all!))
