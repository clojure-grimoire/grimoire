(ns grimoire.web.config
  (:require [detritus.variants :refer [deftag]]
            [grimoire.github :as gh]
            [grimoire.api.web :as web]
            [grimoire.api.fs :as fs]))

(deftag Instance
  "An instance of Grimoire. As a value!"
  [jetty
   simpledb
   lib-grim
   site-config
   web-config
   notes-config])

(def -lib-grim-config
  (fs/->Config "doc-store"
               "notes-store"
               "notes-store"))

(def -site-config
  {:url                 "http://conj.io"
   :repo                "https://github.com/clojure-grimoire/grimoire"
   :base-url            "/"
   :store-url           "/store/v1/"
   :version             (slurp "VERSION")
   :google-analytics-id "UA-44001831-2"
   :year                "2015"
   :author              {:me          "http://arrdem.com"
                         :email       "me@arrdem.com"
                         :gittip      "https://gittip.com/arrdem"}
   :style               {:header-sep  "/"
                         :title       "Grimoire - Community Clojure Documentation"
                         :description "Community documentation of Clojure"
                         :quote       "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."}})

(def -notes-config
  (gh/->repo "clojure-grimoire" "datastore"))

(def -web-config
  (web/->Config (:url -site-config)))

(let [f ->Instance]
  (defn ->Instance [jetty simpledb]
    (f
     ,,jetty
     ,,simpledb
     ,,-lib-grim-config
     ,,-site-config
     ,,-web-config
     ,,-notes-config)))

(def service
  (atom nil))

;; Site configuration
;;------------------------------------------------------------------------------
(defn site-config []
  (:site-config @service))

(defn lib-grim-config []
  (:lib-grim @service))

(defn simpledb-config []
  (:simpledb @service))

(defn notes-config []
  (:notes-config @service))

(def web-config []
  (:web-config @service))
