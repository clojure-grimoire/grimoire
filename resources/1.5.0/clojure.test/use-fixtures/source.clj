(defmulti use-fixtures
  "Wrap test runs in a fixture function to perform setup and
  teardown. Using a fixture-type of :each wraps every test
  individually, while:once wraps the whole run in a single function."
  {:added "1.1"}
  (fn [fixture-type & args] fixture-type))