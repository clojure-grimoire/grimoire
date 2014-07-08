### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
; Use clojure.java.io/resource to read resources from the classpath:

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


