(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/main"]
  :plugins      [[lein-environ "1.0.2"]]
  :exclusions   [org.clojure/clojure]
  :dependencies [[org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure-grimoire/lib-grimoire "0.10.7"]
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
                 [pandect "0.5.4"]]
  
  :aliases {"serve" ["with-profile" "server" "run"]}

  :profiles {:clj-1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :jnt-1.9 {:dependencies [[org.jaunt-lang/jaunt "1.9.0-SNAPSHOT"]]}

             :server  [:clj-1.8
                       {:env  {:url "https://www.conj.io"}
                        :main grimoire.web.service}]

             :dev     [:clj-1.8
                       {:dependencies [[ring/ring-mock "0.3.0"]]
                        :source-paths ["src/dev"]
                        :main         user
                        :env          {:url "http://127.0.0.1:3000"}}]})
