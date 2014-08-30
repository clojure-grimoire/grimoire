(defproject grimoire (slurp "VERSION")
  :description "THIS SHOULD NEVER BE DEPLOYED"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.thnetos/cd-client "0.3.6"]]

  :aliases {"serve" ["with-profile" "server" "run"]}

  :profiles {:_1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :_1.5 {:dependencies [[org.clojure/clojure "1.5.0"]]}
             :_1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}

             :_doc {:main         grimoire.doc
                    :dependencies [[me.raynes/fs "1.4.4"]
                                   [com.cemerick/url "0.1.1"]]}

             :1.4 [:_doc :_1.4]
             :1.5 [:_doc :_1.5]
             :1.6 [:_doc :_1.6]

             :_repl {:plugins [[cider/cider-nrepl "0.8.0-SNAPSHOT"]]}

             :_server {:dependencies [[ring/ring-core "1.3.0"]
                                      [ring/ring-jetty-adapter "1.3.0"]
                                      [selmer "0.6.9"]
                                      [compojure "1.1.8"]
                                      [hiccup "1.0.5"]
                                      [markdown-clj "0.9.47"]
                                      [me.raynes/conch "0.8.0"]
                                      [me.arrdem/detritus "0.1.0"]]
                       :main         grimoire.web.service}

             :_dev {:source-paths ["dev"]}

             :server [:_1.6 :server]

             :user   [:_1.6 :_doc :_repl :_server :_dev]})
