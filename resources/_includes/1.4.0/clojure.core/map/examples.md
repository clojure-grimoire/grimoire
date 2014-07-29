### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (map inc [1 2 3 4 5])
(2 3 4 5 6)


;; map can be used with multiple collections. Collections will be consumed
;; and passed to the mapping function in parallel:
user=> (map + [1 2 3] [4 5 6])
(5 7 9)


;; When map is passed more than one collection, the mapping function will
;; be applied until one of the collections runs out:
user=> (map + [1 2 3] (iterate inc 1))
(2 4 6)



;; map is often used in conjunction with the # reader macro:
user=> (map #(str "Hello " % "!" ) ["Ford" "Arthur" "Tricia"])
("Hello Ford!" "Hello Arthur!" "Hello Tricia!")

;; A useful idiom to pull "columns" out of a collection of collections.
;; Note, it is equivalent to:
;; user=> (map vector [:a :b :c] [:d :e :f] [:g :h :i])

user=> (apply map vector [[:a :b :c]
                          [:d :e :f]
                          [:g :h :i]])

([:a :d :g] [:b :e :h] [:c :f :i])

;; From http://clojure-examples.appspot.com/clojure.core/map{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (map #(vector (first %) (* 2 (second %)))
            {:a 1 :b 2 :c 3})
([:a 2] [:b 4] [:c 6])

user=> (into {} *1)
{:a 2, :b 4, :c 6}
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Use a hash-map as a function to translate values in a collection from the
;; given key to the associated value

user=> (map {2 "two" 3 "three"} [5 3 2])
(nil "three" "two")

;; then use (filter identity... to remove the nils
user=> (filter identity (map {2 "two" 3 "three"} [5 3 2]))
("three" "two"){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; mapping over a hash-map applies (into) first.
;; need to use functions that deal with arrays (fn [[key val]] ...)
(map pprint {:key :val :key1 :val1})
([:key :val]
[:key1 :val1]
nil nil)

;;above, the pprint output appears to be part of the return value but it's not:
(hash-set (map pprint {:key :val :key1 :val1}))
[:key :val]
[:key1 :val1]
#{(nil nil)}

(map second {:key :val :key1 :val1})
(:val :val1){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
(map fn [a 4 x]
        [b 5 y]
        [c 6])
;        ^ ^
; applies fn to a b c as (fn a b c)
; applies fn to 4 5 6 as (fn 4 5 6)
; ignores (x y)
; returns a list of results
; equivalent to (list (fn a b c) (fn 4 5 6))

;example
(map list [1 2 3]
         '(a b c)
         '(4 5))

user=> (map list  [1 2 3] '(a b c) '(4 5))
((1 a 4) (2 b 5))
;same as
user=> (list (list 1 'a 4) (list 2 'b 5))
((1 a 4) (2 b 5)){% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
; map passed two collection arguments. From 4Clojure Problem #157

(def d1 [:a :b :c])
(#(map list % (range)) d1)
((:a 0) (:b 1) (:c 2)){% endraw %}
{% endhighlight %}


