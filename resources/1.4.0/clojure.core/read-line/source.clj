(defn read-line
  "Reads the next line from stream that is the current value of *in* ."
  {:added "1.0"
   :static true}
  []
  (if (instance? clojure.lang.LineNumberingPushbackReader *in*)
    (.readLine ^clojure.lang.LineNumberingPushbackReader *in*)
    (.readLine ^java.io.BufferedReader *in*)))