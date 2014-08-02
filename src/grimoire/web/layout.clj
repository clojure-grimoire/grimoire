(ns grimoire.web.layout
  (:require [hiccup.page :as page]))

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
     [:a.sidebar-nav-item {:href "/static.zip"} "Offline"]
     [:a.sidebar-nav-item {:href "/api"} "API"]
     [:a.sidebar-nav-item {:href "/about"} "About"]
     [:a.sidebar-nav-item {:href "/contributing"} "Contributing"]
     [:a.sidebar-nav-item {:href "https://www.gittip.com/arrdem/"} "Support this project"]
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
