(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.thnetos/cd-client "0.3.6"]]
  :aliases {"serve" ["with-profile" "server" "run"]}
  :profiles {:repl   {:plugins [[cider/cider-nrepl "0.8.0-SNAPSHOT"]]}
             :server {:dependencies [[org.clojure/clojure "1.6.0"]
                                     [org.clojure-grimoire/lib-grimoire "0.1.0-SNAPSHOT"]
                                     [grimradical/clj-semver "0.3.0-SNAPSHOT"]
                                     [cheshire "5.3.1"]
                                     [com.taoensso/timbre "3.2.1"]
                                     [ring/ring-core "1.3.0"]
                                     [ring/ring-jetty-adapter "1.3.0"]
                                     [selmer "0.6.9"]
                                     [compojure "1.1.8"]
                                     [hiccup "1.0.5"]
                                     [markdown-clj "0.9.47"]
                                     [me.raynes/conch "0.8.0"]]
                      :main         grimoire.web.service}
             :dev    {:source-paths ["dev"]
                      :main         user}
             :user   [:repl :server :dev]})
