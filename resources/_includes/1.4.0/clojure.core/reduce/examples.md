### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (reduce + [1 2 3 4 5])
15
user=> (reduce + [])
0
user=> (reduce + 1 [])
1
user=> (reduce + 1 [2 3])
6{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;converting a vector to a set
user=> (reduce conj #{} [:a :b :c])
#{:a :c :b}
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Create a word frequency map out of a large string s.

;; `s` is a long string containing a lot of words :)
(reduce #(assoc %1 %2 (inc (%1 %2 0)))
        {}
        (re-seq #"\w+" s))

; (This can also be done using the `frequencies` function.)
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; Calculate primes until 1000

user=> (reduce
         (fn [primes number]
           (if (some zero? (map (partial mod number) primes))
             primes
             (conj primes number)))
         [2]
         (take 1000 (iterate inc 3)))

[2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97 101 103 107 109 113 127 131 137 139 149 151 157 163 167 173 179 181 191 193 197 199 211 223 227 229 233 239 241 251 257 263 269 271 277 281 283 293 307 311 313 317 331 337 347 349 353 359 367 373 379 383 389 397 401 409 419 421 431 433 439 443 449 457 461 463 467 479 487 491 499 503 509 521 523 541 547 557 563 569 571 577 587 593 599 601 607 613 617 619 631 641 643 647 653 659 661 673 677 683 691 701 709 719 727 733 739 743 751 757 761 769 773 787 797 809 811 821 823 827 829 839 853 857 859 863 877 881 883 887 907 911 919 929 937 941 947 953 967 971 977 983 991 997]{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;; Add one sequence to another:
user=> (reduce conj [1 2 3] [4 5 6])
[1 2 3 4 5 6]
{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
; Combine a set of collections into a single collection
user=> (reduce into [[1 2 3] [:a :b :c] '([4 5] 6)])
[1 2 3 :a :b :c [4 5] 6]{% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure %}
{% raw %}
(defn key-pres?
    "This function accepts a value (cmp-val) and a vector of vectors
    (parsed output from clojure-csv) and returns the match value
    back if found and nil if not found.

    Using reduce, the function searches every vector row to see
    if cmp-val is at the col-idx location in the vector."

    [cmp-val cmp-idx csv-data]
    (reduce
        (fn [ret-rc csv-row]
            (if (= cmp-val (nth csv-row col-idx nil))
                    (conj ret-rc cmp-val)))
        []
        csv-data)){% endraw %}
{% endhighlight %}


### Example 7
[permalink](#example-7)

{% highlight clojure %}
{% raw %}
(defn reduce-csv-row
    "Accepts a csv-row (a vector) a list of columns to extract,
     and reduces (and returns) a csv-row to a subset based on
     selection using the values in col-nums (a vector of integer
     vector positions.)"

    [csv-row col-nums]

    (reduce
        (fn [out-csv-row col-num]
            ; Don't consider short vectors containing junk.
            (if-not (<= (count csv-row) 1)
                (conj out-csv-row (nth csv-row col-num nil))))
        []
        col-nums))

{% endraw %}
{% endhighlight %}


