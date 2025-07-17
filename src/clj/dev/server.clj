(ns dev.server
  (:require
    [ring.adapter.jetty        :refer [run-jetty]]
    [ring.middleware.resource  :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.file :refer [wrap-file]]))


(defn not-found [request] ;
  {:status   404
   :headers  {"content-type" "text/plain; charset=utf-8"}
   :body     (str "Resource not found: " (:uri request))})

(def app (wrap-file not-found "resources/public")) ;

(defn -main [& _]
  (run-jetty app {:port 5173 :join? true}))
