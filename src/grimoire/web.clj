(ns grimoire.web
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [compojure.core :refer [defroutes context GET]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :as page]
            [markdown.core :as md]
            [me.raynes.conch :refer [let-programs]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Site config

(def site-config
  {:base-title          "Grimoire - Clojure Documentation"
   :description         "Community documentation of Clojure"
   :url                 "http://grimoire.arrdem.com"
   :baseurl             "/"
   :version             "0.3.0"
   :clojure-version     "1.6.0"
   :google-analytics-id "UA-44001831-2"
   :year                "2014"
   :author              {:me         "http://arrdem.com/"
                         :email      "me@arrdem.com"
                         :github     "https://github.com/arrdem/grimoire"}
   :style               {:header-sep " &raquo; "}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Layout

(defn header [{:keys [baseurl title base-title]}]
  [:head
   [:link {:rel "profile" :href "http://gmpg.org/xfn/11"}]
   [:meta {:content "IE=edge" :http-equiv "X-UA-Compatible"}]
   [:meta {:content "text/html; charset=utf-8" :http-equiv "content-type"}]
   [:meta {:content "width=device-width initial-scale=1.0 maximum-scale=1":name "viewport"}]
   [:title (when title (str title " &middot; ")) base-title]
   (page/include-css
    (str baseurl "public/css/poole.css")
    (str baseurl "public/css/syntax.css")
    (str baseurl "public/css/lanyon.css"))
   [:link {:href "http://fonts.googleapis.com/css?family=PT+Serif:400,400italic,700|PT+Sans:400"
           :rel "stylesheet"}]
   [:link {:href (str baseurl "public/apple-touch-icon-precomposed.png") :sizes "144x144" :rel "apple-touch-icon-precomposed"}]
   [:link {:href (str baseurl "public/favicon.ico") :rel "shortcut icon"}]])

(defn masthead [{:keys [baseurl base-title]}]
  [:div.masthead
   [:div.container
    [:h3.masthead-title
     [:a {:title "Home" :href baseurl} base-title]
     [:small]]]])

(defn sidebar [{:keys [description author version year]}]
  (list
   [:input#sidebar-checkbox.sidebar-checkbox {:type "checkbox"}]
   [:div#sidebar.sidebar
    [:div.sidebar-item [:p description]]
    [:nav.sidebar-nav
     [:a.sidebar-nav-item {:href "/"} "Home"]
     [:a.sidebar-nav-item {:href "/1.6.0/"} "Clojure 1.6"]
     [:a.sidebar-nav-item {:href "/1.5.0/"} "Clojure 1.5"]
     [:a.sidebar-nav-item {:href "/1.4.0/"} "Clojure 1.4"]
     [:a.sidebar-nav-item {:href "/about"} "About"]
     [:a.sidebar-nav-item {:href "/contributing"} "Contributing"]
     [:br] "More" [:br]
     (let [{:keys [github me]} author]
       (list
        [:a.sidebar-nav-item {:href github} "Github Repo"]
        [:a.sidebar-nav-item {:href me} "About Me"]))]
    [:div.sidebar-item
     [:p "Currently v" version]]
    [:div.sidebar-item
     [:p "© " year ". All rights reserved."]]]))

(defn foot [{:keys [google-analytics-id]}]
  [:footer
   [:script {:type "text/javascript"}
    "(function(document) {
        var toggle = document.querySelector('.sidebar-toggle');
        var sidebar = document.querySelector('#sidebar');
        var checkbox = document.querySelector('#sidebar-checkbox');

        document.addEventListener('click', function(e) {
          var target = e.target;

          if(!checkbox.checked ||
             sidebar.contains(target) ||
             (target === checkbox || target === toggle)) return;

          checkbox.checked = false;
        }, false);
      })(document);"]
   [:script {:type "text/javascript"}
    "var _gaq = _gaq || [];
  _gaq.push(['_setAccount', '" google-analytics-id "']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();"]])

(defn layout
  [page & content]
  (page/html5
   (header page)
   [:body
    (sidebar page)
    [:div.wrap (masthead page)
     [:div {:class "container content"}
      [:div {:class "page"}
       content]]]
    [:label.sidebar-toggle {:for "sidebar-checkbox"}]]
   (foot page)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Pages

(defn cheatsheet [{:keys [baseurl clojure-version]}]
  (-> "cheatsheet.html"
      io/resource
      slurp
      (string/replace #"\{\{ site.baseurl \}\}" baseurl)
      (string/replace #"\{\{ site.clojure_version \}\}" clojure-version)))

(def cheatsheet-memo (memoize cheatsheet))

(def quote
  "Even the most powerful wizard must consult grimoires as an aid against forgetfulness.")

(defn home-page []
  (layout
   site-config
   [:blockquote [:p quote]]
   (cheatsheet-memo site-config)))

(def header-regex #"^---\n((?:[a-z-]+: [^\n]+\n)*)---\n")

(defn parse-markdown-page-header [page]
  (when-let [header (some->> page
                             (re-find header-regex)
                             second)]
    (->> (string/split header #"\n")
         (map #(string/split % #": "))
         (map (juxt (comp keyword first) second))
         (into {}))))

(defn resource-file-contents [file]
  (let [file (io/file file)]
    (when (.exists file)
      (some-> file slurp))))

(defn highlight-clojure [text]
  (let-programs [pygmentize
                 (if (.exists (io/file "./pygmentize"))
                   "./pygmentize"
                   "pygmentize")]
    (pygmentize "-fhtml" (str "-l" "clojure")
                (str "-Ostripnl=False,encoding=utf-8")
                {:dir "resources/pygments"
                 :in text})))

(defn clojure-file [file]
  (some-> file resource-file-contents highlight-clojure))

(defn markdown-file [file]
  (-> file resource-file-contents md/md-to-html-string))

(defn parse-markdown-page [page]
  (when-let [raw (some-> page (str ".md") io/resource slurp)]
    [(or (parse-markdown-page-header raw) {})
     (-> raw (string/replace header-regex "") md/md-to-html-string)]))

(defn markdown-page [page]
  (let [[header page] (parse-markdown-page page)
        site-config (merge site-config header)]
    (layout
     site-config
     (if page
       (list (when-let [title (:title site-config)]
               [:h1 title])
             page)
       (response/not-found "Not found, sorry.")))))

(defn prepare-path [path]
  (-> path
      (string/replace #"resources/" "")
      (string/split #"/")))

(defn paths [& elements]
  (let [dir (apply io/file "resources" elements)]
    (when (.exists dir)
      (->> (.listFiles dir)
           (map str)
           (map prepare-path)))))

(defn symbol-examples [path]
  (let [dir (io/file path)]
    (when (.exists dir)
      (->> (.listFiles dir)
           (map str)))))

(defn version-page [version]
  (layout
   site-config
   (markdown-file (str "resources/" version "/index.md"))
   [:h3 [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]]
   [:h2 "Namespaces"]
   [:ul
    (for [path (paths version)]
      [:li [:a {:href (str (:baseurl site-config) (string/join "/" path) "/")}
            (last path)]])]))

(defn emit-alphabetized-links
  [records]
  (let [segments (group-by (comp str first :name) records)]
    (for [k (sort (keys segments))]
      (list [:h3 (string/capitalize k)]
            [:p
             (for [r (sort-by :name (get segments k))]
               [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defn namespace-page [version namespace]
  (layout
   site-config
   (markdown-file (str "resources/" version "/" namespace "/index.md"))
   [:h1 {:class "page-title"}
    [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]
    (-> site-config :style :header-sep)
    namespace]
   (let [keys                  ["special"        "macro"   "fn"         "var"]
         mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
         ids      (zipmap keys ["sforms",        "macros", "fns",       "vars"])
         link-ids (zipmap keys ["sff",           "mf",     "ff",        "vf"])
         grouping (->> (for [path (paths version namespace)]
                         (let [fp (string/join "/" path)]
                           {:url  (str (:baseurl site-config) fp "/")
                            :name (slurp (io/resource (str fp "/name.txt")))
                            :type (slurp (io/resource (str fp "/type.txt")))}))
                       (group-by :type))]
     (for [k keys]
       (when-let [records (get grouping k)]
         (list
          [:h2 (get mapping k) " "
           [:a {:id (get link-ids k)} "+"]]
          [:div {:id (get ids k)}
           (emit-alphabetized-links records)]))))

   [:script {:src "/public/jquery.js" :type "text/javascript"}]
   [:script {:src "/public/namespace.js" :type "text/javascript"}]))

(def namespace-page-memo
  (memoize namespace-page))

(defn example [index path]
  (let []
    (list
     [:h4 "Example " (inc index)]
     [:div (clojure-file path)])))

(defn symbol-page [version namespace symbol type]
  (let [symbol-file-path (partial str "resources/" version "/" namespace "/" symbol "/")
        name             (slurp (symbol-file-path "name.txt"))]
    (case type
      :html
      (layout
       site-config
       [:h1 {:class "page-title"}
        [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]
        (-> site-config :style :header-sep)
        [:a {:href (str (:baseurl site-config) version "/" namespace "/")} namespace]
        (-> site-config :style :header-sep)
        name]
       [:h2 "Arities"]
       [:p (-> "arities.txt" symbol-file-path resource-file-contents)]
       [:h2 "Official Documentation"]
       [:pre (-> "docstring.md" symbol-file-path markdown-file)]
       (when-let [comdoc  (-> "extended-docstring.md" symbol-file-path markdown-file)]
         (list
          [:h2 "Community Documentation"]
          [:pre comdoc]))
       (when-let [examples (-> "examples" symbol-file-path symbol-examples seq)]
         (list
          [:h2 "Examples"]
          (map-indexed example examples)))
       (when-let [source (-> "source.clj" symbol-file-path clojure-file)]
         (list
          [:h2 "Source"]
          [:div source])))
      :text
      (-> (str version " - " namespace " - " name)
          response/response
          (response/content-type "text/plain")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Routes

(defroutes app
  (GET "/" [] (home-page))
  (GET "/about" [] (markdown-page "about"))
  (GET "/contributing" [] (markdown-page "contributing"))

  (route/resources "/public")

  (context "/:version" [version]
    (GET "/" [version]
         (when (#{"1.4.0" "1.5.0" "1.6.0"} version)
           (version-page version)))

    (context "/:namespace" [namespace]
      (GET "/" [version namespace]
           (namespace-page-memo version namespace))

      (context "/:symbol" [symbol]
        (GET "/" {{header-type :type} :headers
                  {param-type :type} :params}
             (symbol-page version namespace symbol
                          (keyword (or header-type param-type "html")))))))

  (route/not-found
   (layout
    site-config
    (slurp (io/resource "404.html")))))

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

(defn -main [& args]
  (start-web-server!))
