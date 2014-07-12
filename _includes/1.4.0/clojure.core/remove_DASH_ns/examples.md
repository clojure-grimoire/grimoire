### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Let's create a namespace and then remove it

user=> (create-ns 'my-new-namespace)
#<Namespace my-new-namespace>

;; removing a namespace will give you the namespace you just deleted, if one existed
user=> (remove-ns 'my-new-namespace)
#<Namespace my-new-namespace>

;; removing a namespace that does not exist, will tell you that nothing was removed,
;; by returning nil, and won't give any errors
user=> (remove-ns 'my-new-namespace)
nil
{% endraw %}
{% endhighlight %}


