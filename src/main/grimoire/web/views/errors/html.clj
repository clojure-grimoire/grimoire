(ns main.grimoire.web.views.errors.html
  (:require [grimoire.web.views :refer [site-config]]
            [grimoire.web.views.errors :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]))

(defmethod search-no-symbol :text/html [_type version ns]
  (layout site-config
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
  (layout site-config
          [:h1 {:class "page-title"}
           ,,"Search - No symbol"]
          (list
           [:pre (pr-str {:v version})]
           [:p
            "Huh, looks like you tried to use an unknown search version or just forgot the namespace and symbol."
            "See " [:a {:href "/api"} "the API docs"] " for more info."
            "Please file an issue on " [:a {:href (str (:repo site-config) "issues/")}
                                        "the bugtracker"]
            " if you think this is a bug."])))
