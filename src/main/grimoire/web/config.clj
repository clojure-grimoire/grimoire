(ns grimoire.web.config
  (:require [detritus.variants :refer [deftag]]
            [grimoire.api.fs :refer [->Config]]))

(deftag Instance
  "An instance of Grimoire. As a value!"
  [jetty
   simpledb
   lib-grim
   site-config])

(def lib-grim-config
  (->Config "doc-store"
            "notes-store"
            "notes-store"))

(def site-config
  {:url                 "http://conj.io/"
   :repo                "https://github.com/clojure-grimoire/grimoire/"
   :base-url            "/"
   :store-url           "/store/v0/"
   :version             (slurp "VERSION")
   :google-analytics-id "UA-44001831-2"
   :year                "2015"
   :author              {:me          "http://arrdem.com/"
                         :email       "me@arrdem.com"
                         :gittip      "https://gittip.com/arrdem/"}
   :style               {:header-sep  "/"
                         :title       "Grimoire - Community Clojure Documentation"
                         :description "Community documentation of Clojure"
                         :quote       "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."}})

(def service
  (atom (->Instance nil
                    nil
                    lib-grim-config
                    site-config)))
