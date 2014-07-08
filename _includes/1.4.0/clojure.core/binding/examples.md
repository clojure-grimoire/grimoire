### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Here are the definitions.
(defn mymax [x y]
  (min x y))

(defn find-max [x y]
  (max x y))

user=> (let [max mymax]
         (find-max 10 20))

20 ;let is ineffective outside current lexical scope


user=> (binding [max mymax]
         (find-max 10 20))

10 ;because max is now acting as min{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; As of Clojure 1.3, vars need to be explicitly marked as ^:dynamic in order for
;; them to be dynamically rebindable:

user=> (def ^:dynamic x 1)
user=> (def ^:dynamic y 1)
user=> (+ x y)
2

;; Within the scope of the binding, x = 2 and y = 3

user=> (binding [x 2 y 3]
         (+ x y))
5

;; But once you leave the binding's scope, x and y maintain their original
;; bindings:

user=> (+ x y)
2{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;;Use t like a "template"

(declare ^:dynamic t)

(defn addt []
  (+ t 10))

(binding [t 1]
  (addt))
=> 11{% endraw %}
{% endhighlight %}


