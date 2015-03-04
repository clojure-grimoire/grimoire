(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main"]
  :aliases {"serve" ["with-profile" "server" "run"]}
  :profiles {:server
             {:dependencies [[org.clojure/clojure "[1.6.0,)"]
                             [org.clojure-grimoire/lib-grimoire "0.8.0-SNAPSHOT"
                              :exclusions [org.clojure/clojure
                                           me.arrdem/detritus]]
                             [cheshire "5.4.0"
                              :exclusions [org.clojure/clojure]]
                             [com.taoensso/timbre "3.4.0"
                              :exclusions [org.clojure/clojure]]
                             [ring/ring-core "1.3.2"
                              :exclusions [org.clojure/clojure]]
                             [ring/ring-jetty-adapter "1.3.2"
                              :exclusions [org.clojure/clojure]]
                             [selmer "0.8.0"
                              :exclusions [org.clojure/clojure]]
                             [compojure "1.3.1"
                              :exclusions [org.clojure/clojure]]
                             [hiccup "1.0.5"
                              :exclusions [org.clojure/clojure]]
                             [markdown-clj "0.9.62"
                              :exclusions [org.clojure/clojure]]
                             [me.raynes/conch "0.8.0"
                              :exclusions [org.clojure/clojure]]
                             [me.arrdem/detritus "0.2.2-SNAPSHOT"
                              :exclusions [org.clojure/clojure]]
                             [simpledb "0.1.4"
                              :exclusions [org.clojure/clojure]]
                             [sitemap "0.2.4"
                              :exclusions [org.clojure/clojure]]]
              :main         grimoire.web.service}

             :dev
             {:dependencies [[ring/ring-mock "0.2.0"]
                             [acyclic/squiggly-clojure "0.1.2-SNAPSHOT"]]
              :plugins      [[cider/cider-nrepl "0.8.0-SNAPSHOT"]
                             [lein-environ "1.0.0"]]
              :source-paths ["src/dev"]
              :main         user
              :env          {:squiggly {:checkers [:eastwood :typed :kibit]}}}
             
             :user [:server :dev :arrdem]})
