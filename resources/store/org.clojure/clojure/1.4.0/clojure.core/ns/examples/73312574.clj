;; Generating a class so we can call Clojure from Java 
(ns com.domain.tiny
  (:gen-class
    :name com.domain.tiny
    :methods [#^{:static true} [binomial [int int] double]]))

(defn binomial
  "Calculate the binomial coefficient."
  [n k]
  (let [a (inc n)]
    (loop [b 1
           c 1]
      (if (> b k)
        c
        (recur (inc b) (* (/ (- a b) b) c))))))

(defn -binomial
  "A Java-callable wrapper around the 'binomial' function."
  [n k]
  (binomial n k))

(defn -main []
  (println (str "(binomial 5 3): " (binomial 5 3)))
  (println (str "(binomial 10042 111): " (binomial 10042 111))))


;; Calling from Java
import com.domain.tiny;

public class Main {

    public static void main(String[] args) {
        System.out.println("(binomial 5 3): " + tiny.binomial(5, 3));
        System.out.println("(binomial 10042, 111): " + tiny.binomial(10042, 111));
    }
}


;; The result was:
(binomial 5 3): 10.0
(binomial 10042, 111): 4.9068389575068143E263


;; Example was borrowed from clartaq @ Stack Overflow