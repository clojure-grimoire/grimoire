(def ^:dynamic *core-java-api*
  (case (System/getProperty "java.specification.version")
    "1.6" "http://java.sun.com/javase/6/docs/api/"
    "http://java.sun.com/javase/7/docs/api/"))