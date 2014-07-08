### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(defstruct car-struct :make :model :year :color)

(def car (struct car-struct "Toyota" "Prius" 2010))

(def make (accessor car-struct :make))

user=> (make car)  ; Same as both (car :make) and (:make car)
"Toyota"

{% endraw %}
{% endhighlight %}


