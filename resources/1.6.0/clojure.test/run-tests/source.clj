(defn run-tests
  "Runs all tests in the given namespaces; prints results.
  Defaults to current namespace if none given.  Returns a map
  summarizing test results."
  {:added "1.1"}
  ([] (run-tests *ns*))
  ([& namespaces]
     (let [summary (assoc (apply merge-with + (map test-ns namespaces))
                     :type :summary)]
       (do-report summary)
       summary)))