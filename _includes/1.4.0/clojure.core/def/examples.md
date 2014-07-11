### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def my-val 5)
#'user/my-val
user=> my-val
5{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def my-function (fn [x] (* x x x)))
#'user/my-function
user=> (my-function 4)
64{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; This is an example of setting a docstring during a def.
;; (Note that the clojure.repl namespace which contains the
;;  doc function is not loaded by default in Emacs' SLIME mode.)

user> (def ted-nugent "the nuge rocks" 123)
#'user/ted-nugent
user> (doc ted-nugent)
-------------------------
user/ted-nugent
  The nuge rocks
user> ted-nugent
123
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
;; give function another name
user=> (def sys-map map)
;; give macro another name
user=> (def #^{:macro true} sys-loop #'loop){% endraw %}
{% endhighlight %}


