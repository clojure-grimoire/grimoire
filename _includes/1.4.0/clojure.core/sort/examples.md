### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (sort [3 1 2 4])
(1 2 3 4)

user=> (sort > (vals {:foo 5, :bar 2, :baz 10}))
(10 5 2)

;; do not do this, use sort-by instead
user=> (sort #(compare (last %1) (last %2)) {:b 1 :c 3 :a  2})
([:b 1] [:a 2] [:c 3])

;; like this:
user=> (sort-by last {:b 1 :c 3 :a 2})
([:b 1] [:a 2] [:c 3]){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

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
   is superior to the lower price and if the price is same, it is
   superior to the lower id."
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


{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Warning: You can sort a Java array and get back a sorted immutable Clojure
;; data structure, but it will also change the input Java array, by sorting it.
;; Copy the array before sorting if you want to avoid this.

user=> (def x (to-array [32 11]))
#'user/x

user=> (seq x)
(32 11)

user=> (def y (sort x))
#'user/y

;; Return sorted sequence
user=> y
(11 32)

user=> (class y)
clojure.lang.ArraySeq

;; but also modifies x, because it used the array to do the sorting.
user=> (seq x)
(11 32)

;; One way to avoid this is copying the array before sorting:
user=> (def y (sort (aclone x)))
#'user/y{% endraw %}
{% endhighlight %}


