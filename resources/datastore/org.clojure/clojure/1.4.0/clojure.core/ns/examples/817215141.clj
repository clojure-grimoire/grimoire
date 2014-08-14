;; Generate a Java class
(ns org.clojuredocs.test
      (:gen-class))

(defn -main [] (prn "Hello, World!"))


;; After compilation:
sh$ java -cp classes org.clojuredocs.test
Hello, World!
