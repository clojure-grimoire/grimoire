### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
(def xmlzipper (clojure.zip/xml-zip (clojure.xml/parse "resources/somedata.xml")))

;;make a zippper pointing at the children to the topnode in somedata.xml
(clojure.zip/children xmlzipper)






{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
(require '[clojure.zip :as z])

user=> (z/right
        (z/down
         (z/xml-zip
          {:tag :root :content [{:tag :any :content ["foo" "bar"]} "bar"]})))
["bar" {:l [{:content ["foo" "bar"], :tag :any}], :pnodes [{:content [{:content ["foo" "bar"], :tag :any} "bar"], :tag :root}], :ppath nil, :r nil}]

;; The above can also be written like this:
user=> (->
        (z/xml-zip {:tag :root :content [{:tag :any :content ["foo" "bar"]} "bar"]})
        z/down z/right)
["bar" {:l [{:content ["foo" "bar"], :tag :any}], :pnodes [{:content [{:content ["foo" "bar"], :tag :any} "bar"], :tag :root}], :ppath nil, :r nil}]{% endraw %}
{% endhighlight %}


