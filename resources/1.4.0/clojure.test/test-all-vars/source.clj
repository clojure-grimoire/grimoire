(defn test-all-vars
  "Calls test-var on every var interned in the namespace, with fixtures."
  {:added "1.1"}
  [ns]
  (let [once-fixture-fn (join-fixtures (::once-fixtures (meta ns)))
        each-fixture-fn (join-fixtures (::each-fixtures (meta ns)))]
    (once-fixture-fn
     (fn []
       (doseq [v (vals (ns-interns ns))]
         (when (:test (meta v))
           (each-fixture-fn (fn [] (test-var v)))))))))