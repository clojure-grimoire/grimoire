### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> "some string"
"some string"

user=> (str)
""
user=> (str nil)
""
user=> (str 1)
"1"
user=> (str 1 2 3)
"123"
user=> (str 1 'symbol :keyword)
"1symbol:keyword"

;; A very common usage of str is to apply it to an existing collection:
user=> (apply str [1 2 3])
"123"

;; compare it with:
user=> (str [1 2 3])
"[1 2 3]"

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; Destructuring with a string, getting just a few characters from it
user=> (let [[first-char second-char] "abcde"]
         (prn 'first= first-char)
         (prn 'second= second-char))
first= \a
second= \b
nil

;; More destructuring with a string
user=> (let [[first-char second-char & rest-of-chars] "abcde"]
         (prn 'first= first-char)
         (prn 'second= second-char)
         (prn 'rest= rest-of-chars))
first= \a
second= \b
rest= (\c \d \e)
nil

;; Destructuring, getting the first character of a string
;; and then a reference to the entire string
user=> (let [[first-char :as all-the-string] "abcde"]
         (prn 'first= first-char)
         (prn 'all= all-the-string))
first= \a
all= "abcde"
nil{% endraw %}
{% endhighlight %}


