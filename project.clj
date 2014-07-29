(defproject grimoire "0.1.0-SNAPSHOT"
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.thnetos/cd-client "0.3.6"]]

  :profiles {:1.4    {:dependencies [[org.clojure/clojure "1.4.0"]]
                      :main         grimoire.doc}
             :1.5    {:dependencies [[org.clojure/clojure "1.5.0"]]
                      :main         grimoire.doc}
             :1.6    {:dependencies [[org.clojure/clojure "1.6.0"]]
                      :main         grimoire.doc}
             :repl   {:plugins [[cider/cider-nrepl "0.7.0-SNAPSHOT"]]}
             :server {:dependencies [[ring/ring-core "1.3.0"]
                                     [ring/ring-jetty-adapter "1.3.0"]
                                     [selmer "0.6.9"]
                                     [compojure "1.1.8"]
                                     [hiccup "1.0.5"]]
                      :main         grimoire.web}
             :dev    {:source-paths ["dev"]}
             :user   [:1.6 :repl :server :dev :user]})
