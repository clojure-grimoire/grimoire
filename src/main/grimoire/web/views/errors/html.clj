(ns grimoire.web.views.errors.html
  (:require [grimoire.web.views :refer [header]]
            [grimoire.web.config :refer [site-config]]
            [grimoire.web.views.errors :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.things :as t]
            [grimoire.util :as util]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]))

(defmethod search-no-symbol :text/html [_type version ns]
  (layout
   site-config
   [:h1 {:class "page-title"}
    ,,"Search - No symbol"]
   (list
    [:pre (pr-str {:v version :ns ns})]
    [:p
     "Huh, looks like you searched just for a namespace :c"
     "This isn't supported right now. See " [:a {:href "/api"} "the API docs"] " for more info."
     "Please file an issue on " [:a {:href (str (:repo site-config) "issues/")}
                                 "the bugtracker"]
     " if you think this is a bug."])))

(defmethod search-no-version :text/html [_type version]
  (layout
   site-config
   [:h1 {:class "page-title"}
    ,,"Search - No symbol"]
   (list
    [:pre (pr-str {:v version})]
    [:p
     "Huh, looks like you tried to use an unknown search version or just forgot the namespace and symbol."
     "See " [:a {:href "/api"} "the API docs"] " for more info."
     "Please file an issue on "
     [:a {:href (str (:repo site-config) "issues/")}
      "the bugtracker"]
     " if you think this is a bug."])))

(defmethod error-unknown-symbol :text/html [_type def-thing]
  (let [groupid        (t/thing->name (t/thing->group def-thing))
        artifactid     (t/thing->name (t/thing->artifact def-thing))
        version        (t/thing->name (t/thing->version def-thing))
        platform       (t/thing->name (t/thing->platform def-thing))
        namespace      (t/thing->name (t/thing->namespace def-thing))
        symbol         (t/thing->name def-thing)
        version-string (format "[%s/%s \"%s\"] %s" groupid artifactid version platform)
        symbol-string  (format "%s/%s" namespace (util/update-munge symbol))]
    (layout
     site-config
     [:h1 {:class "page-title"}
      (header (:parent def-thing))]
     [:p "Unknown symbol identifier " (pr-str symbol-string)]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo site-config)}
       " github bugtracker."]])))
