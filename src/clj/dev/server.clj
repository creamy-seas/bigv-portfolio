(ns dev.server
  (:require
    [ring.adapter.jetty        :refer [run-jetty]]
    [ring.middleware.resource  :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]))

(def app
  (-> (constantly {:status 404 :body "Not Found"})
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))

(defn -main [& _]
  (run-jetty app {:port 5173 :join? true}))
