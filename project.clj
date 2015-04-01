(defproject org.clojure-grimoire/grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main"]
  :aliases {"serve" ["with-profile" "server" "run"]}
  :profiles {:server
             {:dependencies [[org.clojure/clojure "1.7.0-alpha6"]
                             [org.clojure-grimoire/lib-grimoire "0.9.1"
                              :exclusions [org.clojure/clojure
                                           me.arrdem/detritus]]
                             [org.clojure-grimoire/simpledb "0.1.8"
                              :exclusions [org.clojure/clojure]]
                             [cheshire "5.4.0"
                              :exclusions [org.clojure/clojure]]
                             [com.taoensso/timbre "3.4.0"
                              :exclusions [org.clojure/clojure]]
                             [ring/ring-core "1.3.2"
                              :exclusions [org.clojure/clojure]]
                             [ring/ring-jetty-adapter "1.3.2"
                              :exclusions [org.clojure/clojure]]
                             [selmer "0.8.2"
                              :exclusions [org.clojure/clojure]]
                             [compojure "1.3.2"
                              :exclusions [org.clojure/clojure]]
                             [hiccup "1.0.5"
                              :exclusions [org.clojure/clojure]]
                             [markdown-clj "0.9.65"
                              :exclusions [org.clojure/clojure]]
                             [me.raynes/conch "0.8.0"
                              :exclusions [org.clojure/clojure]]
                             [me.arrdem/detritus "0.2.2"
                              :exclusions [org.clojure/clojure]]
                             [sitemap "0.2.4"
                              :exclusions [org.clojure/clojure]]
                             [instaparse "1.3.6"
                              :exclusions [org.clojure/clojure]]]
              :env          {:url "http://conj.io"}
              :main         grimoire.web.service}

             :dev
             {:dependencies [[ring/ring-mock "0.2.0"]
                             [acyclic/squiggly-clojure "0.1.2-SNAPSHOT"]]
              :plugins      [[cider/cider-nrepl "0.8.0-SNAPSHOT"]
                             [lein-environ "1.0.0"]]
              :source-paths ["src/dev"]
              :main         user
              :env          {:squiggly {:checkers [:eastwood :typed :kibit]}
                             :url      "http://127.0.0.1:3000"}}
             
             :user [:server :dev :arrdem]})
