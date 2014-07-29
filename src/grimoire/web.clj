(ns grimoire.web
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [hiccup.page :as page]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params-middleware]
            [ring.util.response :as response]))

(defn layout [title & content]
  (page/html5
   [:head
    [:title title]]
   [:body
    [:h1 title]
    content]))

;; Routes

;; /, /$VERSION/ and /$VERSION/$NAMESPACE/

(defroutes app
  (GET "/" [] (response/response "hello"))
  (route/resources "/")
  (route/not-found "<h1>Page not found</h1>"))

;; Jetty

(defonce service (atom nil))

(defn start-web-server! []
  (reset! service
          (jetty/run-jetty (params-middleware/wrap-params app)
                           {:port 3000 :join? false})))

(defn stop-web-server! []
  (when-let [service* @service]
    (.stop service*)
    (reset! service nil)))

(defn restart-web-server! []
  (stop-web-server!)
  (start-web-server!))

(defn -main [&args]
  (start-web-server!))
