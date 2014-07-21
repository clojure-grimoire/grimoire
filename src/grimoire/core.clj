(ns grimoire.core
  (:require [grimoire.doc :as doc]
            [grimpore.site :as site]))

(def usage
"Usage: lein run [help|doc ns-file|serve]

doc:
  Requires a file, the first form of which is a Seq of Symbols. The
  namespaces represented by these symbols are required, and all vars
  documented. Generates the appropriate documentation files in
  resources/ and exits.

serve:
  Runs a webserver, expecting to see data files as build by multiple
  invocations of doc.

help:
  Prints this message and exits.")

(defn -main
  [cmd & args]
  (case cmd
    ("help")  (do (print usage)
                  (System/exit 0))
    ("doc")   (apply doc/-main args)
    ("serve") (apply site/-main args)))
