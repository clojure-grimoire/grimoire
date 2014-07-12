### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (replace [:zeroth :first :second :third :fourth] [0 2 4 0])
[:zeroth :second :fourth :zeroth]

user=> (replace [10 9 8 7 6] [0 2 4])
[10 8 6]{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (replace '{0 ZERO, 1 ONE, 2 TWO} '(This is the code â€” 0 1 2 0))
(This is the code â€” ZERO ONE TWO ZERO)

user=> (replace {2 :two, 4 :four} [4 2 3 4 5 6 2])
[:four :two 3 :four 5 6 :two]
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
; Behaves somewhat similar to map, but notice the differences
user=> (map [:zeroth :first :second :third :fourth] [0 2 4 0])
(:zeroth :second :fourth :zeroth)

; 1. replace returns a vector, while map returns a seq
; 2. replace keeps unmatched values, while map replace with nil
user=> (map {} [0])
(nil)
user=> (map [] [0])
IndexOutOfBoundsException   clojure.lang.PersistentVector.arrayFor (PersistentVector.java:107)
{% endraw %}
{% endhighlight %}


