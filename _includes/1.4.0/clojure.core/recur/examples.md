### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(def factorial
  (fn [n]
    (loop [cnt n acc 1]
       (if (zero? cnt)
            acc
          (recur (dec cnt) (* acc cnt))
; in loop cnt will take the value (dec cnt)
; and acc will take the value (* acc cnt)
)))){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
; A loop that sums the numbers 10 + 9 + 8 + ...

; Set initial values count (cnt) from 10 and down
(loop [sum 0 cnt 10]
    ; If count reaches 0 then exit the loop and return sum
    (if (= cnt 0)
    sum
    ; Otherwise add count to sum, decrease count and
    ; use recur to feed the new values back into the loop
    (recur (+ cnt sum) (dec cnt)))){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
(loop [i 0]
  (when (< i 5)
    (println i)
    (recur (inc i)); loop i will take this value
)){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
(defn compute-across [func elements value]
  (if (empty? elements)
    value
    (recur func (rest elements) (func value (first elements)))))

(defn total-of [numbers]
  (compute-across + numbers 0))

(defn larger-of [x y]
  (if (> x y) x y))

(defn greatest-of [numbers]
  (compute-across larger-of numbers (first numbers))){% endraw %}
{% endhighlight %}


