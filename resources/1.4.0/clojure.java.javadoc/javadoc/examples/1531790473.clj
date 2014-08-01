user=> (use 'clojure.java.javadoc)
nil

user=> (javadoc String)
"http://java.sun.com/javase/6/docs/api/java/lang/String.html"

user=> (javadoc (java.util.Date.))
"http://java.sun.com/javase/6/docs/api/java/util/Date.html"
