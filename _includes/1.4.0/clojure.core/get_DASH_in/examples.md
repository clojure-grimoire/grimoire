### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; We can use get-in for reaching into nested maps:
user=> (def m {:username "sally"
               :profile {:name "Sally Clojurian"
                         :address {:city "Austin" :state "TX"}}})
#'user/m

user=> (get-in m [:profile :name])
"Sally Clojurian"
user=> (get-in m [:profile :address :city])
"Austin"
user=> (get-in m [:profile :address :zip-code])
nil
user=> (get-in m [:profile :address :zip-code] "no zip code!")
"no zip code!"


;; Vectors are also associative:
user=> (def v [[1 2 3]
               [4 5 6]
               [7 8 9]])
#'user/v
user=> (get-in v [0 2])
3
user=> (get-in v [2 1])
8


;; We can mix associative types:
user=> (def mv {:username "jimmy"
                :pets [{:name "Rex"
                        :type :dog}
                       {:name "Sniffles"
                        :type :hamster}]})
#'user/mv
user=> (get-in mv [:pets 1 :type])
:hamster
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(def s1 [[:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
         [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
         [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(def cols [0 2 3])

(defn f1
  [s1 col]
  (map #(get-in s1 [% col] nil) (range (count s1))))

(apply interleave (map (partial f1 s1) cols))

(:000-00-0000 "JACKSON" "FRED" :000-00-0001 "SIMPSON" "HOMER" :000-00-0002 "SMITH" "SUSAN"){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; spam link removed{% endraw %}
{% endhighlight %}


