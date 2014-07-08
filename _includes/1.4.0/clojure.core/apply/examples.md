### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def *strings* ["str1" "str2" "str3"])
#'user/*strings*

;; Oops!
user=> (str *strings*)
"[\"str1\" \"str2\" \"str3\"]"

;; Yay!
user=> (apply str *strings*)
"str1str2str3"
user=>

;; Note the equivalence of the following two forms
user=> (apply str ["str1" "str2" "str3"])
"str1str2str3"

user=> (str "str1" "str2" "str3")
"str1str2str3"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; If you were to try
user=> (max [1 2 3])
[1 2 3]

;; You would get '[1 2 3]' for the result. In this case, 'max' has received one
;; vector argument, and the largest of its arguments is that single vector.

;; If you would like to find the largest item **within** the vector, you would need
;; to use `apply`

user=> (apply max [1 2 3])
3

;; which is the same as (max 1 2 3)
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Here's an example that uses the optional second argument, args:

user=> (apply map vector [[:a :b] [:c :d]])
([:a :c] [:b :d])

;; In this example, 'f' = 'map', 'args' = 'vector', and argseq = '[:a :b] [:c :d]',
;; making the above code equivalent to

user=> (map vector [:a :b] [:c :d])
([:a :c] [:b :d]) ;Same answer as above

;; It might help to think of 'map' and 'vector' "slipping inside" the argument list
;; ( '[[:a :b] [:c :d]]' ) to give '[map vector [:a :b] [:c :d]]' , which then
;; becomes the executable form '(map vector [:a :b] [:c :d])' .{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
;; only functions can be used with apply.  'and' is a macro
;; because it needs to evaluate its arguments lazily and so
;; does not work with apply.
user=> (apply and (list true true false true)

-> ERROR

{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
;apply is used to apply an operator to its operands.

(apply + '(1 2))  ; equal to (+ 1 2)
=> 3


;you can also put operands before the list of operands and they'll be consumed in the list of operands

(apply + 1 2 '(3 4))  ; equal to (apply + '(1 2 3 4))
=> 10{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure linenos %}
{% raw %}
;; You can use map and apply together to drill one level deep in a collection
;; of collections, in this case returning a collection of the max of each
;; nested collection

user=> (map #(apply max %) [[1 2 3][4 5 6][7 8 9]])
(3 6 9){% endraw %}
{% endhighlight %}


