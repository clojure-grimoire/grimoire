(ns grimoire.web.layout
  (:require [hiccup.page :as page]))

(defn header [{:keys [baseurl] :as c}]
  [:head
   [:link {:rel "profile" :href "http://gmpg.org/xfn/11"}]
   [:meta {:content "IE=edge" :http-equiv "X-UA-Compatible"}]
   [:meta {:content "text/html; charset=utf-8" :http-equiv "content-type"}]
   [:meta {:content "width=device-width initial-scale=1.0 maximum-scale=1":name "viewport"}]
   (when-let [pg (:page c)]
     (list
      (when-let [description (:description pg)]
        [:meta {:name "description"
                :content description}])
      (when-let [summary (:summary pg)]
        [:meta {:name "summary"
                :content summary}])))
   [:title (-> c :style :title)]
   (page/include-css
    (str baseurl "public/css/poole.css")
    (str baseurl "public/css/syntax.css")
    (str baseurl "public/css/lanyon.css")
    (str baseurl "public/css/headings.css"))
   [:link {:href "http://fonts.googleapis.com/css?family=PT+Serif:400,400italic,700|PT+Sans:400"
           :rel "stylesheet"}]
   [:link {:href (str baseurl "public/apple-touch-icon-precomposed.png") :sizes "144x144" :rel "apple-touch-icon-precomposed"}]
   [:link {:href (str baseurl "public/favicon.ico") :rel "shortcut icon"}]])

(defn masthead [{:keys [baseurl] :as c}]
  [:div.masthead
   [:div.container
    [:h3.masthead-title
     [:a {:title "Home" :href baseurl} (-> c :style :title)]
     [:small]]]])

(defn sidebar [{:keys [repo description author version year]}]
  (list
   [:input#sidebar-checkbox.sidebar-checkbox {:type "checkbox"}]
   [:div#sidebar.sidebar
    [:div.sidebar-item [:p description]]
    [:nav.sidebar-nav
     [:a.sidebar-nav-item {:href "/"} "Home"]
     [:a.sidebar-nav-item {:href "/store/"} "Artifact store"]
     [:a.sidebar-nav-item {:href "/store/org.clojure/clojure/1.6.0/"} "Clojure 1.6"]
     [:a.sidebar-nav-item {:href "/store/org.clojure/clojure/1.5.0/"} "Clojure 1.5"]
     [:a.sidebar-nav-item {:href "/store/org.clojure/clojure/1.4.0/"} "Clojure 1.4"]
     [:a.sidebar-nav-item {:href "/articles"} "Article store"]
     [:a.sidebar-nav-item {:href "/articles/about"} "About"]
     [:a.sidebar-nav-item {:href "/articles/api"} "API"]
     [:a.sidebar-nav-item {:href "/articles/contributing"} "Contributing"]
     [:br] "More" [:br]
     (let [{:keys [gittip me]} author]
       (list
        [:a.sidebar-nav-item {:href repo} "Github Repo"]
        [:a.sidebar-nav-item {:href me} "About Me"]
        [:a.sidebar-nav-item {:href gittip} "Support This Project"]))]
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
