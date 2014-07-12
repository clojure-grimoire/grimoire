### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Array's component type is set to (class 2), cannot add Strings.
;; This will result in an IllegalArgumentException
user=> (into-array [2 "4" "8" 5])
;; Evaluation aborted.

;; However, if the common type is specified, aforementioned values can be put into an array
user=> (into-array Object [2 "4" "8" 5])
#<Object[] [Ljava.lang.Object;@3aa6d0a4>

user=> (into-array (range 4))
#<Integer[] [Ljava.lang.Integer;@63d6dc46>

;; if you assign a type, you still have to coerce values
user=> (into-array Byte/TYPE (range 4))
;; Evaluation aborted.

user=> (into-array Byte/TYPE (map byte (range 4)))
#<byte[] [B@68ffefc9>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; java.nio.file.Paths#get takes (String, String...)
user=> (java.nio.file.Paths/get "/Users" (into-array ["username" "dev" "clojure"]))
#<UnixPath /Users/username/dev/clojure>{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Creating an empty array defaults to Object[]
user=> (into-array [])
#<Object[] [Ljava.lang.Object;@21f1151f>

;; However, the type of an empty array can be coerced
user=> (into-array String [])
#<String[] [Ljava.lang.String;@578baf24>
{% endraw %}
{% endhighlight %}


