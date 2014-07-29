### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; The following defines a lazy-seq of all positive numbers.  Note that
;; the lazy-seq allows us to make a recursive call in a safe way because
;; the call does not happen immediately but instead creates a closure.

user=> (defn positive-numbers
	([] (positive-numbers 1))
	([n] (cons n (lazy-seq (positive-numbers (inc n))))))
#'user/positive-numbers

user=> (take 5 (positive-numbers))
(1 2 3 4 5)

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; A lazy-seq of Fibonacci numbers (fn = fn-1 + fn-2)
;; The producer function takes exactly two parameters
;; (because we need the last 2 elements to produce a new one)
user=> (defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))

user=> (take 5 (fib 1 1))
(1 1 2 3 5){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; It might be easier to think about the producer function as a function
;; that, given element n, produces element n+1 via a recursive call to
;; itself, wrapped with lazy-seq to delay its execution
;; We might also provide no-argument version of the function that calls
;; itself for the first element(s) of the sequence being generated.
;; => variant of fibonaci with a no-arg version and using cons first:
(defn sum-last-2
   ([] (sum-last-2 1 2))
   ([n m] (cons n (lazy-seq (sum-last-2 m (+ n m))))))

user=> (take 6 (sum-last-2))
(1 2 3 5 8 13){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; An example combining lazy sequences with higher order functions
;; Generate prime numbers using trial division.
;; Note that the starting set of sieved numbers should be
;; the set of integers starting with 2 i.e., (iterate inc 2)
(defn sieve [s]
  (cons (first s)
        (lazy-seq (sieve (filter #(not= 0 (mod % (first s)))
                                 (rest s))))))

user=> (take 20 (sieve (iterate inc 2)))
(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71)
{% endraw %}
{% endhighlight %}


