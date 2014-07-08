### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Note that even though println returns nil, doto still returns the HashMap object
user> (doto (java.util.HashMap.)
            (.put "a" 1)
            (.put "b" 2)
            (println))
#<HashMap {b=2, a=1}>
{"b" 2, "a" 1}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; quick demonstration of using a Collections function on the resulting ArrayList

user=> (def al (doto (java.util.ArrayList.) (.add 11) (.add 3)(.add 7)))
#'user/al
user=> al
#<ArrayList [11, 3, 7]>
user=> (java.util.Collections/sort al)
nil
user=> al
#<ArrayList [3, 7, 11]>
user=>{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; careful when calling 'dotimes' from within a 'doto' statement
user=> (doto (java.util.ArrayList.)
             (.add -2)
             (.add -1)
             (dotimes [i 3] (.add i)))
java.lang.IllegalArgumentException: dotimes requires a vector for its binding (NO_SOURCE_FILE:1)
; what has happened is that (java.util.ArrayList.) has secretly become the first argument to 'dotimes' and thus the exception informs us that it can't find the binding vector required for 'dotimes' to expand. You can cure this behaviour by simply using 'do' instead of 'doto' or by wrapping the call to 'dotimes' in a function. e.g:

;using 'let' with implicit 'do' instead of 'doto'
user=> (let [al (java.util.ArrayList.)]
         (.add al -2)
         (.add al -1)
         (dotimes [i 3] (.add al i))
         al);return the ArrayList
#<ArrayList [-2, -1, 0, 1, 2]>  ;exactly what we intended

;wrapping 'dotimes' in a function literal
user=>(doto (java.util.ArrayList.)
            (.add -2)
            (.add -1)
            (#(dotimes [i 3] (.add % i))))
#<ArrayList [-2, -1, 0, 1, 2]>  ;exactly what we intended again
{% endraw %}
{% endhighlight %}


