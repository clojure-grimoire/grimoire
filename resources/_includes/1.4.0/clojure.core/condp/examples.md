### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Taken from the excellent clojure tutorial:
;; http://java.ociweb.com/mark/clojure/article.html

user=> (print "Enter a number: ")
user=> (flush) ; stays in a buffer otherwise
user=> (let [reader (java.io.BufferedReader. *in*) ; stdin
             line (.readLine reader)
             value (try
                     (Integer/parseInt line)
                     (catch NumberFormatException e line))] ;use string val if not int
         (println
           (condp = value
             1 "one"
             2 "two"
             3 "three"
             (str "unexpected value, \"" value \")))
         (println
           (condp instance? value
             Number (* value 2)
             String (* (count value) 2))))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (condp some [1 2 3 4]
         #{0 6 7} :>> inc
         #{4 5 9} :>> dec
         #{1 2 3} :>> #(+ % 3))

3{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (condp some [1 2 3 4]
         #{0 6 7} :>> inc
         #{5 9}   :>> dec)

java.lang.IllegalArgumentException: No matching clause: [1 2 3 4]{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
user=> (condp (comp seq re-seq) "foo=bar"
         #"[+](\w+)"    :>> #(vector (-> % first (nth 1) keyword) true)
         #"[-](\w+)"    :>> #(vector (-> % first (nth 1) keyword) false)
         #"(\w+)=(\S+)" :>> #(let [x (first %)]
                               [(keyword (nth x 1)) (nth x 2)]))

[:foo "bar"]{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
;;this is with liberator
;;branching on request method
(defresource my-resource
  :exists? (fn [{:keys [db] {query-params :query-params
                             body :body
                             method :request-method}
                 :request}]

             (condp = method
               :get (my-get-exists-fn)
               :post (my-post-exists-fn)))){% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure %}
{% raw %}
;; a recursive function to calculate length
;; same as 'count'
(defn length [lst]
    (condp = lst
        (list) 0 ; if empty list result 0
        (+ 1 (length (rest lst))))) ; default expression

user=> (length '(1 2 3))

user=> 3{% endraw %}
{% endhighlight %}


### Example 7
[permalink](#example-7)

{% highlight clojure %}
{% raw %}
(condp #(%1 2 %2) 3
  = "eq"
  < "lt"
  > "gt")
"lt"{% endraw %}
{% endhighlight %}


