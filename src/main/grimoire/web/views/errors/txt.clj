(ns grimoire.web.views.errors.txt
  (:require [clojure.java.io :as io]
            [grimoire.web.config :refer [site-config]]
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

(defmethod error-unknown-symbol :text/plain [_type def-thing]
  (let [groupid        (t/thing->name (t/thing->group def-thing))
        artifactid     (t/thing->name (t/thing->artifact def-thing))
        version        (t/thing->name (t/thing->version def-thing))
        platform       (t/thing->name (t/thing->platform def-thing))
        namespace      (t/thing->name (t/thing->namespace def-thing))
        symbol         (t/thing->name def-thing)
        version-string (format "[%s/%s \"%s\"] %s" groupid artifactid version platform)
        symbol-string  (format "%s/%s" namespace (util/update-munge symbol))]
    (-> (str "In artifact: " version-string \newline
             "Unknown symbol: " symbol-string  \newline
             "Sorry! Only clojure.core is documented right now.")
        response/response
        (response/content-type "text/plain"))))
