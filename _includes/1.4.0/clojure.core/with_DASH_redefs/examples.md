### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> [(type []) (class [])]
[clojure.lang.PersistentVector clojure.lang.PersistentVector]

user=> (with-redefs [type (constantly java.lang.String)
                     class (constantly 10)]
         [(type [])
          (class [])])
[java.lang.String 10]{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(ns http)

(defn post [url]
  {:body "Hello world"})

(ns app
  (:require [clojure.test :refer [deftest is run-tests]]))

(deftest is-a-macro
  (with-redefs [http/post (fn [url] {:body "Goodbye world"})]
    (is (= {:body "Goodbye world"} (http/post "http://service.com/greet")))))

(run-tests) ;; test is passing{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; be careful, with-redefs can permanently change a var if applied concurrently:

user> (defn ten [] 10)
#'user/ten
user> (doall (pmap #(with-redefs [ten (fn [] %)] (ten)) (range 20 100)))
...
user> (ten)
79{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
;; redefine var
(def foo 1)
#'user/foo
(with-redefs [foo 2] foo)
2

;; redefine private var
(ns first)
(def ^:private foo 1)
#'first/foo

(ns second)
(with-redefs [first/foo 2] @#'first/foo)
2

;; @#' is the macros of (deref (var first/foo))
(with-redefs [first/foo 2] (deref (var first/foo))
2{% endraw %}
{% endhighlight %}


