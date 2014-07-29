### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Require clojure.java.io and call its file function:

user=> (require '(clojure.java io))
user=> (clojure.java.io/file "filename")
#&lt;File filename&gt;{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; alias clojure.java.io as io
user=> (require '[clojure.java.io :as io])
nil

user=> (io/file "Filename")
#<File Filename>

;; alias clojure.java.io as io using prefixes
user=> (require '(clojure.java [io :as io2])
nil

user=> (io2/file "Filename")
#<File Filename>{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(ns rosettacode.24game
  (:require [clojure.string :as str]))

(defn parse-infix-data
  "input '1+2+3+4'
   output (1 + 2 + 3 + 4)
   where the numbers are clojure numbers, and the symbols are clojure operators"
  [string] (map read-string (next (str/split string #"")))){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
(require '(clojure.contrib [sql :as sql])){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
(ns myproject.core
  (:use [clojure.core] :reload)
  (:require [clojure.string :as str :refer [replace]] :reload-all))

(str/replace "foo" #"o" "e")
"fee"{% endraw %}
{% endhighlight %}


