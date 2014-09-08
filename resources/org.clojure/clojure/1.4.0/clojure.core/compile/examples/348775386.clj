user=> (compile (symbol "clojure.java.io")
clojure.java.io

user=> (compile (symbol "unexistent.namespace")
FileNotFoundException Could not locate unexistent/namespace__init.class or unexistent/namespace.clj on classpath:   clojure.lang.RT.load (RT.java:432)