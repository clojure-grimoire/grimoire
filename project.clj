(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/main"]
  :plugins      [[lein-environ "1.1.0"]]
  :exclusions   [org.clojure/clojure]
  :dependencies [[org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure-grimoire/lib-grimoire "0.10.9"]
                 [org.clojure-grimoire/simpledb "0.1.10"]
                 [cheshire "5.5.0"]
                 [com.taoensso/timbre "4.2.1"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [selmer "1.0.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [markdown-clj "0.9.85"]
                 [me.raynes/conch "0.8.0"]
                 [sitemap "0.2.5"]
                 [instaparse "1.4.1"]
                 [environ "1.0.2"]
                 [pandect "0.5.4"]
                 [clj-fuzzy "0.3.0"]]

  :aliases {"serve" ["with-profile" "server" "run"]}

  :profiles {:clj-1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :jnt-0.1 {:dependencies [[org.jaunt-lang/jaunt "0.1.0"]]}
             :jnt-0.2 {:dependencies [[org.jaunt-lang/jaunt "0.2.0"]]}
             :jnt-0.3 {:dependencies [[org.jaunt-lang/jaunt "0.3.0-SNAPSHOT"]]}

             :env-defaults {:env {:fundraising-active      False
                                  :fundraising-rate        10
                                  :simpledb-file           "analytics.db"
                                  :simpledb-write-interval 1
                                  :grimoire-verson         :project/version}}

             :server [:clj-1.8
                      :env-defaults
                      {:env {:url           "https://www.conj.io"
                             :simpledb-file "/srv/www/grimoire/analytics.db"}}
                      {:main grimoire.web.service}]

             :dev [:clj-1.8
                   :env-defaults
                   {:env {:url "http://127.0.0.1:3000"
                          :dev true}}
                   {:dependencies [[ring/ring-mock "0.3.0"]]
                    :source-paths ["src/dev"]
                    :main         user}]})
