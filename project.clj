(defproject grimoire "0.1.0-SNAPSHOT"
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.thnetos/cd-client "0.3.6"]]

  :profiles {:1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]
                   :main grimoire.doc}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.0"]]
                   :main grimoire.doc}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]
                   :main grimoire.doc}
             :server {:dependencies [[compojure "1.1.8"]
                                     [hiccup "1.0.5"]]
                      :main grimoire.site}})
