### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; create hash map the long way
user=> (hash-map)
{}

;; create hash map the short way
user=> {}
{}

;; sending a key more times, will remap it to the last value
user=> (hash-map :key1 1, :key1 2)
{:key1 2}

user=> {:key1 1, :key1 2}
IllegalArgumentException Duplicate key: :key1  clojure.lang.PersistentArrayMap.createWithCheck (PersistentArrayMap.java:70)


user=> (hash-map :key1 'val1, 'key2 :val2, [:compound :key] nil)
{[:compound :key] nil, :key1 val1, key2 :val2}

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (map #(hash-map % 0) (seq "abcdefgh"))
({\a 0} {\b 0} {\c 0} {\d 0} {\e 0} {\f 0} {\g 0} {\h 0})

user=> (apply hash-map (.split "a 1 b 2 c 3" " "))
{"a" "1", "b" "2", "c" "3"}{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
; a hash map can be stored in a var by using `def`
user=> (def person {:name "Steve" :age 24 :salary 7886 :company "Acme"})
#'user/person
user=> person
{:age 24, :name "Steve", :salary 7886, :company "Acme"}{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
; Take a sequence of sequences (vector of vectors, and create a map
; using date as the map key.
user=> (def csv1 [["01/01/2012" 1 2 3 4]["06/15/2012" 38 24 101]])

user=> (map #(hash-map (keyword (first %1)) (vec (rest %1))) csv1)
{:01/01/2012 [1 2 3 4]} {:06/15/2012 [38 24 101]}

; merge the list of maps into a single map
user=> (apply merge '({"01/01/2012" [1 2 3 4]} {"06/15/2012" [38 24 101]}))
{"06/15/2012" [38 24 101], "01/01/2012" [1 2 3 4]}

{% endraw %}
{% endhighlight %}


