### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;;getting meta-data for a defined symbol (as opposed to what it's pointing to.

user=> meta
#<core$meta clojure.core$meta@2e257f1b>

user=> (var meta)
#'clojure.core/meta

user=> (meta (var meta))
{:ns #<Namespace clojure.core>, :name meta, :file "clojure/core.clj", :line 178, :arglists ([obj]), :doc "Returns the metadata of obj, returns nil if there is no metadata.", :added "1.0"}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
=> *clojure-version*
{:major 1, :minor 5, :incremental 0, :qualifier "RC17"}
=> var
;CompilerException java.lang.RuntimeException: Unable to resolve symbol: var in this context, compiling:(NO_SOURCE_PATH:1:42)
=> (var)
;CompilerException java.lang.NullPointerException, compiling:(NO_SOURCE_PATH:1:1)
=> (var 1)
;CompilerException java.lang.ClassCastException: java.lang.Long cannot be cast to clojure.lang.Symbol, compiling:(NO_SOURCE_PATH:1:1)
=> (var defn)
#'clojure.core/defn
=> (var defn 1 2 3 4)
#'clojure.core/defn
{% endraw %}
{% endhighlight %}


