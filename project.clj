(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/main"]
  :plugins      [[lein-environ "1.0.2"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.match "0.3.0-alpha4"
                  :exclusions [org.clojure/clojure]]
                 [org.clojure-grimoire/lib-grimoire "0.10.5"
                  :exclusions [org.clojure/clojure]]
                 [org.clojure-grimoire/simpledb "0.1.10"
                  :exclusions [org.clojure/clojure]]
                 [cheshire "5.5.0"
                  :exclusions [org.clojure/clojure]]
                 [com.taoensso/timbre "4.2.1"
                  :exclusions [org.clojure/clojure]]
                 [ring/ring-core "1.4.0"
                  :exclusions [org.clojure/clojure]]
                 [ring/ring-jetty-adapter "1.4.0"
                  :exclusions [org.clojure/clojure]]
                 [selmer "1.0.0"
                  :exclusions [org.clojure/clojure]]
                 [compojure "1.4.0"
                  :exclusions [org.clojure/clojure]]
                 [hiccup "1.0.5"
                  :exclusions [org.clojure/clojure]]
                 [markdown-clj "0.9.85"
                  :exclusions [org.clojure/clojure]]
                 [me.raynes/conch "0.8.0"
                  :exclusions [org.clojure/clojure]]
                 [sitemap "0.2.5"
                  :exclusions [org.clojure/clojure]]
                 [instaparse "1.4.1"
                  :exclusions [org.clojure/clojure]]
                 [environ "1.0.2"
                  :exclusions [org.clojure/clojure]]]
  
  :aliases {"serve" ["with-profile" "server" "run"]}

  :profiles {:server {:env  {:url "https://wwww.conj.io"}
                      :main grimoire.web.service}

             :dev    {:dependencies [[ring/ring-mock "0.3.0"]]
                      :source-paths ["src/dev"]
                      :main         user
                      :env          {:url "http://127.0.0.1:3000"}}})
