### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; print-dup can be used for basic serialization
;; the following methods write/read clojure forms to/from a file

(defn to-file
  "Save a clojure form to a file"
  [#^java.io.File file form]
  (with-open [w (java.io.FileWriter. file)]
    (print-dup form w)))

(defn from-file
  "Load a clojure form from file."
  [#^java.io.File file]
  (with-open [r (java.io.PushbackReader. (java.io.FileReader. file))]
     (read r))){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; print-dup is a multimethod, you can extend it to support new types.
;; The following statement adds print-dup support to
;; the java.util.Date class
(defmethod print-dup java.util.Date [o w]
  (print-ctor o (fn [o w] (print-dup (.getTime  o) w)) w)) {% endraw %}
{% endhighlight %}


