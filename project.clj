(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/main"]
  :plugins      [[lein-environ "1.1.0"]]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure-grimoire/lib-grimoire "0.10.9"]
                 [org.clojure-grimoire/simpledb "0.2.1"]
                 [cheshire "5.8.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [selmer "1.11.3"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [markdown-clj "1.0.1"]
                 [me.raynes/conch "0.8.0"]
                 [sitemap "0.2.5"]
                 [instaparse "1.4.8"]
                 [environ "1.1.0"]
                 [pandect "0.6.1"]
                 [clj-fuzzy "0.4.1"]]

  :aliases {"serve" ["with-profile" "server" "run"]}

  :profiles {:env-defaults {:env {:fundraising-active      False
                                  :fundraising-rate        10
                                  :simpledb-file           "analytics.db"
                                  :simpledb-write-interval 1
                                  :grimoire-verson         :project/version}}

             :server [:env-defaults
                      {:env {:url           "https://www.conj.io"
                             :simpledb-file "/srv/www/grimoire/analytics.db"}}
                      {:main grimoire.web.service}]

             :dev [:env-defaults
                   {:env {:url "http://127.0.0.1:3000"
                          :dev true}}
                   {:dependencies [[ring/ring-mock "0.3.2"]]
                    :source-paths ["src/dev"]
                    :main         user}]})
