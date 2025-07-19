(ns dev.server
  (:require
   [ring.adapter.jetty        :refer [run-jetty]]
   [ring.middleware.file      :refer [wrap-file]]
   [ring.middleware.file-info :refer [wrap-file-info]]
   [ring.middleware.not-modified :refer [wrap-not-modified]]))

(defn not-found [request] ;
  {:status   404
   :headers  {"content-type" "text/plain; charset=utf-8"}
   :body     (str "Resource not found: " (:uri request))})

(def app
  (-> not-found
      (wrap-file "resources/public")
      wrap-file-info
      wrap-not-modified))

(defn -main [& _]
  (run-jetty app {:port 5173 :join? true}))
