### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Generate a Java class
(ns org.clojuredocs.test
      (:gen-class))

(defn -main [] (prn "Hello, World!"))


;; After compilation:
sh$ java -cp classes org.clojuredocs.test
Hello, World!
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Let's create a namespace and then assign it as the current namespace
user=> (create-ns 'my-new-namespace)
#&lt;Namespace my-new-namespace&gt;

user=> (ns 'my-new-namespace)
java.lang.ClassCastException: clojure.lang.PersistentList cannot be cast to clojure.lang.Symbol (NO_SOURCE_FILE:26)
;; oops, this is not the way to do it; if create-ns needs a symbol, ns does not

user=> (ns my-new-namespace)
nil

my-new-namespace=>
;; it worked as the current namespace is our newly created one

{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
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


;; Example was borrowed from clartaq @ Stack Overflow{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; Create a namespace named demo.namespace.
(ns demo.namespace)

;; Clojure recommends namespaces be at least "two segments" (ie, they should have at least one '.') otherwise it will create a class in the "default package", which is discouraged.

;; If this declaration appears in a file named "demo/namespace.clj" present in your classpath, it is known as a "lib", "demo/namespace.clj" is the lib's "root resource". See http://clojure.org/libs

;; From a clean repl you can load the lib using
user=>(require 'demo.namespace)
; or
user=>(use 'demo.namespace){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;; This example will illustrate changing between namespaces at the repl

;; At the repl, the ns macro can be used to create a namespace, but it is used to change the current namespace (be careful of typos)
user=>(ns demo.namespace)
nil
demo.namespace=> ; The prompt at the repl is now "demo.namespace" reflecting that the current namespace is no longer "user".

;; Add a new function to demo.namespace
demo.namespace=>(defn foo [] (prn "Hello from demo.namespace"))
#'demo.namespace/foo

;; From within "demo.namespace" we can use foo without qualifying it
demo.namespace=>(foo)
"Hello from demo.namespace"
nil

;; Switch back to the "user" namespace
demo.namespace=>(ns user)
nil

;; We can no longer use "foo" without qualification
user=> (foo)
java.lang.Exception: Unable to resolve symbol: foo in this context (NO_SOURCE_FILE:4)

user=> (demo.namespace/foo)
"Hello from demo.namespace"
nil

;; The public symbols of "demo.namespace" can be "referred into" the "user" namespace if desired
user=> (refer 'demo.namespace)
nil

;; foo is now an alias in the "user" namespace which refers to the "demo.namespace/foo" symbol
user=> (foo)
"Hello from demo.namespace"
nil{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
(ns rosettacode.24game
  (:require [clojure.string :as str])
  (:use clojure.test))

(deftest test
 (is (= "ABC" (str/capitalize "abc"))){% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure %}
{% raw %}
;; Multiple required namespaces with aliases
(ns demo.namespace
  (:require [com.example.httplib :as httplib]
            [com.example.otherlib :as otherlib]))
{% endraw %}
{% endhighlight %}


### Example 7
[permalink](#example-7)

{% highlight clojure %}
{% raw %}
;; In clojure 1.4 and higher you can use the refer function from within
;; a require which is equivalent to (:use foo only [...]) but still
;; allows you to reference the required namespace:
(ns my.ns.example
    (:require [my.lib :refer [function1 function2]]))

;; And :refer :all is equivalent to :use :
(ns my.ns.example
    (:require [my.lib :refer :all]))
{% endraw %}
{% endhighlight %}


### Example 8
[permalink](#example-8)

{% highlight clojure %}
{% raw %}
(ns foo.bar
  (:refer-clojure :exclude [ancestors printf])
  (:require [clojure.contrib sql sql.tests])
  (:use [my.lib this that])
  (:import [java.util Date Timer Random]
    (java.sql Connection Statement))){% endraw %}
{% endhighlight %}


