### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; create an array of 10 characters (initially set to blank by default)
;; and set one of the elements to the character "a"

user=> (def cs (char-array 10))
#'user/cs
user=> (vec cs)
[\  \  \  \  \  \  \  \  \  \ ]
user=> (aset-char cs 3 \a)
\a
user=> (vec cs)
[\  \  \  \a \  \  \  \  \  \ ]
user=>{% endraw %}
{% endhighlight %}


