(ns grimoire.web
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [compojure.core :refer [defroutes context GET]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :as page]
            [ring.adapter.jetty :as jetty]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Site config

(def site-config
  {:title               "Grimoire"
   :description         "Community documentation of Clojure"
   :url                 "http://grimoire.arrdem.com"
   :baseurl             "/"
   :version             "0.3.0"
   :clojure-version     "1.6.0"
   :google-analytics-id "UA-44001831-2"
   :author              {:me     "http://arrdem.com/"
                         :email  "me@arrdem.com"
                         :github "https://github.com/arrdem/grimoire"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Layout

(defn header [{:keys [baseurl title]}]
  [:head
   [:link {:rel "profile" :href "http://gmpg.org/xfn/11"}]
   [:meta {:content "IE=edge" :http-equiv "X-UA-Compatible"}]
   [:meta
    {:content "text/html; charset=utf-8" :http-equiv "content-type"}]
   [:meta {:content "width=device-width initial-scale=1.0 maximum-scale=1":name "viewport"}]
   [:title title]
   [:link
    {:href (str baseurl "public/css/poole.css")
     :rel "stylesheet"}]
   [:link
    {:href (str baseurl "public/css/syntax.css")
     :rel "stylesheet"}]
   [:link
    {:href (str baseurl "public/css/lanyon.css")
     :rel "stylesheet"}]
   [:link
    {:href "http://fonts.googleapis.com/css?family=PT+Serif:400400italic700|PT+Sans:400"
     :rel "stylesheet"}]
   [:link
    {:href (str baseurl "public/apple-touch-icon-precomposed.png")
     :sizes "144x144"
     :rel "apple-touch-icon-precomposed"}]
   [:link
    {:href (str baseurl "public/favicon.ico")
     :rel "shortcut icon"}]
   [:link
    {:href "/atom.xml":title "RSS":type "application/rss+xml":rel "alternate"}]])

(defn masthead [{:keys [baseurl title]}]
  [:header.site-header
   [:div.wrap
    [:a.site-title {:href baseurl} title]]])

(defn sidebar [{:keys [description author version year]}]
  [:div#sidebar.sidebar
   [:div.sidebar-item [:p description]]
   [:nav.sidebar-nav
    [:a.sidebar-nav-item {:href "/"} "Home"]
    [:a.sidebar-nav-item {:href "/1.6.0/"} "Clojure 1.6"]
    [:a.sidebar-nav-item {:href "/1.5.0/"} "Clojure 1.5"]
    [:a.sidebar-nav-item {:href "/1.4.0/"} "Clojure 1.4"]
    [:a.sidebar-nav-item {:href "/about/"} "About"]
    [:a.sidebar-nav-item {:href "/contributing/"} "Contributing"]
    [:br] "More" [:br]
    (let [{:keys [github me]} author]
      (list
       [:a.sidebar-nav-item {:href github} "Github Repo"]
       [:a.sidebar-nav-item {:href me} "About Me"]))]
   [:div.sidebar-item
    [:p "Currently v" version]]
   [:div.sidebar-item
    [:p "© " year ". All rights reserved."]]])

(defn foot [{:keys [google-analytics-id]}]
  [:script {:type "text/javascript"}
   "var _gaq = _gaq || [];
  _gaq.push(['_setAccount', '" google-analytics-id "']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();"])

(defn layout
  [page & content]
  (page/html5
   (header page)
   [:body
    (sidebar page)
    [:wrap (masthead page)
     [:div {:class "container content"} content]]]
   (foot page)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Pages

(defn cheatsheet [{:keys [baseurl clojure-version]}]
  (-> "_includes/cheatsheet.html"
      io/resource
      slurp
      (string/replace #"\{\{ site.baseurl \}\}" baseurl)
      (string/replace #"\{\{ site.clojure_version \}\}" clojure-version)))

(def cheatsheet-memo (memoize cheatsheet))

(defn home-page []
  (layout
   site-config
   [:blockquote
    [:p
     "Even the most powerful wizard must consult grimoires as an aid against forgetfulness"]]
   (cheatsheet-memo site-config)))

(defn version-page [version]
  (layout
   site-config
   version))

(defn namespace-page [version namespace]
  (layout
   site-config
   version " - " namespace))

(defn symbol-page [version namespace symbol type]
  (case type
    :html
    (layout
     site-config
     version " - " namespace " - " symbol)
    :text
    (-> (str version " - " namespace " - " symbol)
        response/response
        (response/content-type "text/plain"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Routes

(defroutes app
  (GET "/" []
       (home-page))

  (route/resources "/public")

  (context "/:version" [version]
    (GET "/" [version]
         (version-page version))

    (context "/:namespace" [namespace]
      (GET "/" [version namespace]
           (namespace-page version namespace))

      (context "/:symbol" [symbol]
        (GET "/" {{header-type :type} :headers
                  {param-type :type} :params}
             (symbol-page version namespace symbol
                          (keyword (or header-type param-type "html")))))))

  (route/not-found "<h1>Page not found</h1>"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Web server

(defonce service (atom nil))

(defn start-web-server! []
  (reset! service
          (jetty/run-jetty (handler/site app)
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
