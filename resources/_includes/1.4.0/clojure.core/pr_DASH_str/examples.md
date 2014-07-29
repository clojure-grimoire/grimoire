### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def x [1 2 3 4 5])
#'user/x
user=> x
[1 2 3 4 5]


;; Turn that data into a string...
user=> (pr-str x)
"[1 2 3 4 5]"


;; ...and turn that string back into data!
user=> (read-string (pr-str x))
[1 2 3 4 5]
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; you can think of pr-str as the inverse of read-string
;; turn string into symbols
user=> (read-string "(a b foo :bar)")
(a b foo :bar)

;;turn symbols into a string
user=> (pr-str '(a b foo :bar))
"(a b foo :bar)"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(defn write-object
  "Serializes an object to disk so it can be opened again later.
   Careful: It will overwrite an existing file at file-path."
  [obj file-path]
    (with-open [wr (writer file-path)]
      (.write wr (pr-str obj))))){% endraw %}
{% endhighlight %}


