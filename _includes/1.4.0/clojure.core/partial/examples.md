### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def to-english (partial clojure.pprint/cl-format nil "~@(~@[~R~]~^ ~A.~)"))
#'user/to-english

user=> (to-english 1234567890)
"One billion, two hundred thirty-four million, five hundred sixty-seven thousand, eight hundred ninety"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def hundred-times (partial * 100))
#'user/hundred-times

user=> (hundred-times 5)
500

user=> (hundred-times 4 5 6)
12000

user=> (def add-hundred (partial + 100))
#'user/add-hundred

user=> (add-hundred 5)
105
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
(def subtract-from-hundred (partial - 100))

user=> (subtract-from-hundred 10)      ; same as (- 100 10)
90

user=> (subtract-from-hundred 10 20)   ; same as (- 100 10 20)
70{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
; Maps exponent to coefficient
; x^3 + 2x + 1
(def poly (fn [n]
			(cond
				(= 0 n) 1
				(= 1 n) 2
				(= 3 n) 1
				:else 0)
			)
)

; Differentiates input by returning a polynomial that is curried
; 3x^2 + 2
(defn diff [p]
		(partial (fn [p n] (* (+ 1 n) (p (+ 1 n)))) p)
	)

(poly 3)
;=> 1
((diff poly) 3)
;=> 0
((diff poly) 2)
;=> 3
{% endraw %}
{% endhighlight %}


