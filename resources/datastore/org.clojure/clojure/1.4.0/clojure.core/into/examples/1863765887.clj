(defn test-key-inclusion-cols
  "return all values in column1 that arent' in column2"
  [column1 column2]
  (filter (complement (into #{} column2)) column1))
