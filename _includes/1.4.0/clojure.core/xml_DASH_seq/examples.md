### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; data.xml from https://github.com/clojure/data.xml/
(use '[clojure.data.xml :only [parse-str]])

user=> (let [xml-text "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                      <foo key=\"val\">1<bar>2</bar>3</foo>"]
         (let [root (parse-str xml-text)]
           (xml-seq root)))

({:tag :foo,
  :attrs {:key "val"},
  :content ("1" {:tag :bar, :attrs {}, :content ("2")} "3")}
 "1"
 {:tag :bar, :attrs {}, :content ("2")}
 "2"
 "3")
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(use '[clojure.xml :only [parse]])

;; clojure.xml/parse requires string to be ByteArrayInputStream
user-> (let [xml-text "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                      <foo key=\"val\">1<bar>2</bar>3</foo>"]
         (let [input (java.io.ByteArrayInputStream.
                        (.getBytes xml-text))]
           (let [root (parse input)]
             (xml-seq root))))

({:tag :foo,
  :attrs {:key "val"},
  :content ("1" {:tag :bar, :attrs {}, :content ("2")} "3")}
 "1"
 {:tag :bar, :attrs {}, :content ("2")}
 "2"
 "3"){% endraw %}
{% endhighlight %}


