(defn test-var
  "If v has a function in its :test metadata, calls that function,
  with *testing-vars* bound to (conj *testing-vars* v)."
  {:dynamic true, :added "1.1"}
  [v]
  (when-let [t (:test (meta v))]
    (binding [*testing-vars* (conj *testing-vars* v)]
      (do-report {:type :begin-test-var, :var v})
      (inc-report-counter :test)
      (try (t)
           (catch Throwable e
             (do-report {:type :error, :message "Uncaught exception, not in assertion."
                      :expected nil, :actual e})))
      (do-report {:type :end-test-var, :var v}))))