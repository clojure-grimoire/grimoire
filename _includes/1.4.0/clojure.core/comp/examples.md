### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def negative-quotient (comp - /))
#'user/negative-quotient

user=> (negative-quotient 8 3)
-8/3


user=> (def concat-and-reverse (comp (partial apply str) reverse str))
#'user/concat-and-reverse

user=> (concat-and-reverse "hello" "clojuredocs")
"scoderujolcolleh"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> ((comp str +) 8 8 8)
"24"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
user=> (map
         (comp - (partial + 3) (partial * 2))
         [1 2 3 4])
; returns
(-5 -7 -9 -11){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
user=> (filter (comp not zero?) [0 1 0 2 0 3 0 4])
(1 2 3 4){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
;; make a struct 'goods'. it assumes that every goods has
;; its id number and price.
(defstruct goods :id :price)

;; generate data.
(def data (map #(struct goods %1 %2)
	       (shuffle (range 0 10)) (shuffle
				       (into (range 100 500 100)
					     (range 100 500 100)))))

(defn comp-goods-price
  "a compare function by :price of the struct 'goods.' the sort order
   is that the lower price is superior to the higher one and if the
   price is same, the lower id is superior to the higher one."
  [el1 el2]
  (if (or  (< (:price el1) (:price el2))
           (and (= (:price el1) (:price el2))(< (:id el1) (:id el2))))
    true
    false))

user> data
({:id 1, :price 300} {:id 6, :price 100} {:id 3, :price 100} {:id 4, :price 400} {:id 0, :price 300} {:id 2, :price 200} {:id 5, :price 200} {:id 8, :price 400})
user> (sort (comp comp-goods-price) data)
({:id 3, :price 100} {:id 6, :price 100} {:id 2, :price 200} {:id 5, :price 200} {:id 0, :price 300} {:id 1, :price 300} {:id 4, :price 400} {:id 8, :price 400})
user> (sort-by :price < data) ; compare this with the above.
({:id 6, :price 100} {:id 3, :price 100} {:id 2, :price 200} {:id 5, :price 200} {:id 1, :price 300} {:id 0, :price 300} {:id 4, :price 400} {:id 8, :price 400})

;; Yet another example of 'comp' by PriorityBlockingQueue.

user> (import [java.util.concurrent PriorityBlockingQueue])
java.util.concurrent.PriorityBlockingQueue
user> (def pqdata (new PriorityBlockingQueue 8
		       (comp comp-goods-price)))
#'user/pqdata
user> (doseq [x data]
	     (.add pqdata x))
nil
user> (dotimes [_ 8]
	       (println (.poll pqdata)))
{:id 3, :price 100}
{:id 6, :price 100}
{:id 2, :price 200}
{:id 5, :price 200}
{:id 0, :price 300}
{:id 1, :price 300}
{:id 4, :price 400}
{:id 8, :price 400}
nil
user> {% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure linenos %}
{% raw %}
user=> (def countif (comp count filter))
#'user/countif
user=> (countif even? [2 3 1 5 4])
2{% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure linenos %}
{% raw %}
; Get 2nd to last element from a list
user=> ( (comp second reverse) '("a" 2 7 "b"))
7{% endraw %}
{% endhighlight %}


