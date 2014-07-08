### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; make up a hierarchy a beagle is a sporting breed is a dog is a quadraped is an
;; animal

user=> (derive ::quadruped ::animal)
nil
user=> (derive ::dog ::quadruped)
nil
user=> (derive ::sporting_breed ::dog)
nil
user=> (derive ::beagle ::sporting_breed)
nil
user=> (ancestors ::beagle)
#{:user/dog :user/sporting_breed :user/animal :user/quadruped}
user=>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; use ancestors to show which classes ArrayList derives from and which
;; interfaces it implements

user=> (ancestors java.util.ArrayList)
#{java.util.Collection java.util.AbstractList java.io.Serializable java.lang.Cloneable java.util.List java.lang.Object java.util.AbstractCollection java.util.RandomAccess java.lang.Iterable}
user=>{% endraw %}
{% endhighlight %}


