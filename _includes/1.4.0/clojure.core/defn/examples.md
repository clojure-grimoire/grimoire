### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (defn foo [a b c]
	    (* a b c))
#'user/foo
user=> (foo 1 2 3)
6

user=> (defn bar [a b & [c]]
         (if c
           (* a b c)
           (* a b 100)))
#'user/bar
user=> (bar 5 6)
3000
user=> (bar 5 6 2)
60

user=> (defn baz [a b & {:keys [c d] :or {c 10 d 20}}]
         (* a b c d))
#'user/baz
user=> (baz 2 3)
1200
user=> (baz 2 3 :c 5)
600
user=> (baz 2 3 :c 5 :d 6)
180

user=> (defn boo [a b & {:keys [c d] :or {c 10 d 20} :as all-specified}]
          (println all-specified)
          (* a b c d))
#'user/boo
user=> (boo 2 3)
nil
1200
user=> (boo 2 3 :c 5)
{:c 5}
600
user=> (boo 1 2 :d 3 :c 4)
{:c 4, :d 3}
24
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (defn bar
         ([a b]   (bar a b 100))
         ([a b c] (* a b c)))
#'user/bar
user=> (bar 5 6)
3000
user=> (bar 5 6 2)
60
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; You can use destructuring to have keyword arguments. This would be a
;; pretty verbose version of map (in an example a bit more verbose than
;; the first above):

(defn keyworded-map [& {function :function sequence :sequence}]
  (map function sequence))

;; You can call it like this:

user=> (keyworded-map :sequence [1 2 3] :function #(+ % 2))
(3 4 5)


;; The declaration can be shortened with ":keys" if your local variables
;; should be named in the same way as your keys in the map:

(defn keyworded-map [& {:keys [function sequence]}]
  (map function sequence))
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
(defn somefn
  [req1 req2 ;required params
   & {:keys [a b c d e] ;optional params
      :or {a 1 ;optional params with preset default values other than the nil default
                  ; b takes nil if not specified on call
            c 3 ; c is 3 when not specified on call
            d 0 ; d is 0 --//--
                  ; e takes nil if not specified on call
           }
      :as mapOfParamsSpecifiedOnCall ;takes nil if no extra params(other than the required ones) are specified on call
      }]
  (println req1 req2 mapOfParamsSpecifiedOnCall a b c d e)
  )

=> (somefn 9 10 :b 2 :d 4)
;9 10 {:b 2, :d 4} 1 2 3 4 nil
nil
=> (somefn)
;ArityException Wrong number of args (0) passed to: funxions$somefn  ;clojure.lang.AFn.throwArity (AFn.java:437)
=> (somefn 9 10)
;9 10 nil 1 nil 3 0 nil
nil
=> (somefn 9 10 :x 123)
;9 10 {:x 123} 1 nil 3 0 nil
nil
=> (somefn 9 10 123)
;IllegalArgumentException No value supplied for key: 123  ;clojure.lang.PersistentHashMap.create (PersistentHashMap.java:77)
=> (somefn 9 10 123 45)
;9 10 {123 45} 1 nil 3 0 nil
nil
=> (try
     (somefn 9 10 123)
     (catch IllegalArgumentException e (println "caught:" e)))
;caught: #<IllegalArgumentException java.lang.IllegalArgumentException: No value supplied for key: 123>
nil{% endraw %}
{% endhighlight %}


