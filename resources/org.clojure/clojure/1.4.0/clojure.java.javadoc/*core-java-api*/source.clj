(def ^:dynamic *core-java-api*
  (case (System/getProperty "java.specification.version")
    "1.5" "http://java.sun.com/j2se/1.5.0/docs/api/"
    "1.6" "http://java.sun.com/javase/6/docs/api/"
    "http://java.sun.com/javase/7/docs/api/"))