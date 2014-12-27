(ns main.grimoire.web.views.errors.txt
  (:require [clojure.java.io :as io]
            [grimoire.web.views :refer [site-config]]
            [grimoire.web.views.errors :refer :all]
            [grimoire.web.layout :refer [layout]]
            [grimoire.util :as util]
            [grimoire.things :as t]
            [ring.util.response :as response]))

(defmethod search-no-symbol :text/plain [_type version ns]
  (-> (str "# Search " (pr-str {:v version :ns ns}) "\n\n"
           "Huh, looks like you searched just for a namespace :c\n"
           "This isn't supported right now. See http://conj.io/api for more info.\n"
           "Please file an issue on the bugtracker if you think this is a bug.\n")
      response/response
      (response/content-type "text/plain")))

(defmethod search-no-version :text/plain [_type version]
  (-> (str "# Search - Unknown search API version " (pr-str {:v version}) "\n\n"
           "Huh, looks like you tried to use an unknown search version or forgot the namespace and symbol :c\n"
           "See http://conj.io/api for a list of all search API versions.\n"
           "Please file an issue on the bugtracker if you think this is a bug.\n")
      response/response
      (response/content-type "text/plain")))
