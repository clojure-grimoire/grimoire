(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main"]
  :aliases {"serve" ["with-profile" "server" "run"]}
  :profiles {:repl   {:plugins      [[cider/cider-nrepl "0.8.0-SNAPSHOT"]
                                     [lein-environ "1.0.0"]]
                      :dependencies [[acyclic/squiggly-clojure "0.1.2-SNAPSHOT"]]
                      :env          {:squiggly {:checkers [:eastwood :typed :kibit]}}}
             :server {:dependencies [[org.clojure/clojure "1.6.0"]
                                     [org.clojure-grimoire/lib-grimoire "0.7.0-SNAPSHOT"
                                      :exclusions [org.clojure/clojure]]
                                     [cheshire "5.4.0"
                                      :exclusions [org.clojure/clojure]]
                                     [com.taoensso/timbre "3.3.1"
                                      :exclusions [org.clojure/clojure]]
                                     [ring/ring-core "1.3.2"
                                      :exclusions [org.clojure/clojure]]
                                     [ring/ring-jetty-adapter "1.3.2"
                                      :exclusions [org.clojure/clojure]]
                                     [selmer "0.7.7"
                                      :exclusions [org.clojure/clojure]]
                                     [compojure "1.3.1"
                                      :exclusions [org.clojure/clojure]]
                                     [hiccup "1.0.5"
                                      :exclusions [org.clojure/clojure]]
                                     [markdown-clj "0.9.58"
                                      :exclusions [org.clojure/clojure]]
                                     [me.raynes/conch "0.8.0"
                                      :exclusions [org.clojure/clojure]]
                                     [me.arrdem/detritus "0.2.0"
                                      :exclusions [org.clojure/clojure]]]
                      :main         grimoire.web.service}
             :dev    {:source-paths ["src/dev"]
                      :main         user}
             :user   [:repl :server :dev :arrdem]})
