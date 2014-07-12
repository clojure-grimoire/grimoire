### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
(ns http)

(defn post [url]
  {:body "Hello world"})

(ns app
  (:require [clojure.test :refer [run-tests]]))

(deftest is-a-fn
  (with-redefs-fn {#'http/post (fn [url] {:body "Hello world again"})}
    #(is (= {:body "Hello world again"} (http/post "http://service.com/greet")))))

(run-tests) ;; test is passing{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
=> (defn f [] false)

=> (println (f))
;; false

=> (with-redefs-fn {#'f (fn [] true)}
     #(println (f)))
;; true{% endraw %}
{% endhighlight %}


