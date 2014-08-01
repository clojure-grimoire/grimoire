(defn write-file []
  (with-open [w (clojure.java.io/writer  "f:/w.txt" :append true)]
    (.write w (str "hello" "world"))))