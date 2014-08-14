(defn start-suite
  [name]
  (let [[package classname] (package-class name)]
    (start-element 'testsuite true (suite-attrs package classname))))