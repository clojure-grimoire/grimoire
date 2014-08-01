(use '[clojure.test :only [is]])

user=> (is (true? true))
true

;; false assertions print a message and evaluate to false

user=> (is (true? false))
FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
expected: (true? false)
  actual: (not (true? false))
false

