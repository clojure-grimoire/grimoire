### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Checking numbers
user=> (type 10)
java.lang.Long

user=> (type 10.0)
java.lang.Double


;; Checking collections
user=> (type [10 20])
clojure.lang.PersistentVector

user=> (type '(10 20))
clojure.lang.PersistentList


;; Checking other, but somewhat intuitive, forms
user=> (type :a)
clojure.lang.Keyword

user=> (type Thread)
java.lang.Class


;; Checking a symbol
user=> (type 'whatever)
clojure.lang.Symbol

;; A surprise attack yields
user=> (type clojure.lang.Symbol)
;; not such a surprising response
java.lang.Class


;; Checking a function
user=> (defn foo [] ("any string"))
#'user/foo
user=> (type foo)
user$foo


;; Checking a macro
user=> (type fn)
user$fn

user=> (type clojure.core/fn)
java.lang.Exception: Can't take value of a macro: #'clojure.core/fn (NO_SOURCE_FILE:94)

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;This example demonstrates how to add type information to regular clojure maps
(defn purchase-order [id date amount]
  ^{:type ::PurchaseOrder} ;metadata
   {:id id :date date :amount amount})

(def my-order (purchase-order 10 (java.util.Date.) 100.0))

(my-order)
=> {:id 10, :date #<Date Sun May 15 14:29:19 EDT 2011>, :amount 100.0}

(type my-order)
=> PurchaseOrder{% endraw %}
{% endhighlight %}


