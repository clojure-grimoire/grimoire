### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (clojure.java.io/file "/tmp/foo")
#<File /tmp/foo>

user> (clojure.java.io/file "http://asdf.com")
#<File http:/asdf.com>

user> (clojure.java.io/file "/tmp/foo" "bar")
#<File /tmp/foo/bar>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
; Use clojure.java.io to read in resources from the classpath

(ns rescue.core
  (:require [clojure.java.io :as io] ))

; Populate the file on the command line:
;   echo "Hello Resources!" > resources/hello.txt
(def data-file (io/file
                 (io/resource
                   "hello.txt" )))
(defn -main []
  (println (slurp data-file)) )
; When do "lein run"
; => Hello Resources!{% endraw %}
{% endhighlight %}


