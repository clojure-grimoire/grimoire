(defmacro deftest-
  "Like deftest but creates a private var."
  {:added "1.1"}
  [name & body]
  (when *load-tests*
    `(def ~(vary-meta name assoc :test `(fn [] ~@body) :private true)
          (fn [] (test-var (var ~name))))))