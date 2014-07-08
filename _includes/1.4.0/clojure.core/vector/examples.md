### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; create an empty vector the long way
user=> (vector)
[]

;; create an empty vector the short way
user=> []
[]

;; you can even create vectors with nil values
user=> (vector nil)
[nil]

;; create a vector the long way
user=> (vector 1 2 3)
[1 2 3]

;; create a vector the short way
user=> [1 2 3]
[1 2 3]

;; checking for the 2 results above
user=> (class (vector 1 2 3))
clojure.lang.PersistentVector

user=> (class [1 2 3])
clojure.lang.PersistentVector

user=> (= (vector 1 2 3) [1 2 3])
true

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; Destructuring with a vector, inside a "let" form, a simple case (a symbol
;; for each element):

;; destructuring with an inline vector
user=> (let [[first-element second-element third-element fourth-element]
             [10 20 30 40]]
         (str "first=" first-element " second=" second-element
           " third=" third-element " fourth=" fourth-element))
"first=10 second=20 third=30 fourth=40"
;; notice how 4 symbols were created pointing to the scalars 10, 20, 30 and 40


;; destructuring with a symbol to a vector
user=> (def my-vector [1 2 3 4])
#'user/my-vector

user=> (let [[first-element second-element third-element fourth-element] my-vector]
         (str "first=" first-element " second=" second-element
           " third=" third-element " fourth=" fourth-element))
"first=1 second=2 third=3 fourth=4"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Destructuring with a vector, inside a "let" form, more complex cases:

user=> (let [[first-element second-element & the-rest] my-vector]
         (str "first=" first-element " second=" second-element "
           the-rest=" the-rest))
"first=1 second=2 the-rest=(3 4)"
;; notice how "the-rest" is a sequence

user=> (let [[first-element second-element third-element fourth-element
               :as everything]
             my-vector]
         (str "first=" first-element " second=" second-element "
           third=" third-element " fourth=" fourth-element "
           everything=" everything))
"first=1 second=2 third=3 fourth=4 everything=[1 2 3 4]"
;; notice how "everything" is the whole vector{% endraw %}
{% endhighlight %}


