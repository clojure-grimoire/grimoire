### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (for [x [0 1 2 3 4 5]
             :let [y (* x 3)]
             :when (even? y)]
         y)
(0 6 12)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def digits (seq [1 2 3]))
user=> (for [x1 digits x2 digits] (* x1 x2))
(1 2 3 2 4 6 3 6 9){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
user=> (for [x ['a 'b 'c]
             y [1 2 3]]
          [x y])

([a 1] [a 2] [a 3] [b 1] [b 2] [b 3] [c 1] [c 2] [c 3]){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
user=> (for [x (range 1 6)
             :let [y (* x x)
                   z (* x x x)]]
         [x y z])

([1 1 1] [2 4 8] [3 9 27] [4 16 64] [5 25 125])
{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
(for [x (range 3 7)]
    (* x x))

(9 16 25 36){% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure linenos %}
{% raw %}
user=> (for [[x y] '([:a 1] [:b 2] [:c 0]) :when (= y 0)] x)
(:c)
{% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure linenos %}
{% raw %}
; Demonstrating difference between :when and :while

user=> (time (dorun (for [x (range 1000) y (range 10000) :when (> x y)] [x y])))
"Elapsed time: 2898.908 msecs"
nil

user=> (time (dorun (for [x (range 1000) y (range 10000) :while (> x y)] [x y])))
"Elapsed time: 293.677 msecs"
nil{% endraw %}
{% endhighlight %}


### Example 7
[permalink](#example-7)

{% highlight clojure linenos %}
{% raw %}
; Demonstrating difference between :when and :while
user=> (for [x (range 3) y (range 3) :when (not= x y)] [x y])
([0 1] [0 2] [1 0] [1 2] [2 0] [2 1])
user=> (for [x (range 3) y (range 3) :while (not= x y)] [x y])
([1 0] [2 0] [2 1]){% endraw %}
{% endhighlight %}


### Example 8
[permalink](#example-8)

{% highlight clojure linenos %}
{% raw %}
;; More examples illustrating the difference between :when and :while

;; Simple but inefficient method of checking whether a number is
;; prime.
user=> (defn prime? [n]
         (not-any? zero? (map #(rem n %) (range 2 n))))
#'user/prime?

user=> (range 3 33 2)
(3 5 7 9 11 13 15 17 19 21 23 25 27 29 31)

;; :when continues through the collection even if some have the
;; condition evaluate to false, like filter
user=> (for [x (range 3 33 2) :when (prime? x)]
         x)
(3 5 7 11 13 17 19 23 29 31)

;; :while stops at the first collection element that evaluates to
;; false, like take-while
user=> (for [x (range 3 33 2) :while (prime? x)]
         x)
(3 5 7)

;; The examples above can easily be rewritten with filter or
;; take-while.  When you have a for with multiple binding forms, so
;; that the iteration occurs in a nested fashion, it becomes possible
;; to write something briefly with 'for' that would be more verbose or
;; unwieldy with nested filter or take-while expressions.

user=> (for [x (range 3 17 2) :when (prime? x)
             y (range 3 17 2) :when (prime? y)]
         [x y])
([ 3 3] [ 3 5] [ 3 7] [ 3 11] [ 3 13]
 [ 5 3] [ 5 5] [ 5 7] [ 5 11] [ 5 13]
 [ 7 3] [ 7 5] [ 7 7] [ 7 11] [ 7 13]
 [11 3] [11 5] [11 7] [11 11] [11 13]
 [13 3] [13 5] [13 7] [13 11] [13 13])

user=> (for [x (range 3 17 2) :while (prime? x)
             y (range 3 17 2) :while (prime? y)]
         [x y])
([3 3] [3 5] [3 7]
 [5 3] [5 5] [5 7]
 [7 3] [7 5] [7 7])

;; This example only gives a finite result because of the :while
;; expressions.
user=> (for [x (range) :while (< x 10)
             y (range) :while (<= y x)]
         [x y])

([0 0]
 [1 0] [1 1]
 [2 0] [2 1] [2 2]
 [3 0] [3 1] [3 2] [3 3]
 [4 0] [4 1] [4 2] [4 3] [4 4]
 [5 0] [5 1] [5 2] [5 3] [5 4] [5 5]
 [6 0] [6 1] [6 2] [6 3] [6 4] [6 5] [6 6]
 [7 0] [7 1] [7 2] [7 3] [7 4] [7 5] [7 6] [7 7]
 [8 0] [8 1] [8 2] [8 3] [8 4] [8 5] [8 6] [8 7] [8 8]
 [9 0] [9 1] [9 2] [9 3] [9 4] [9 5] [9 6] [9 7] [9 8] [9 9])
{% endraw %}
{% endhighlight %}


### Example 9
[permalink](#example-9)

{% highlight clojure linenos %}
{% raw %}
;; Here are a couple of examples where the only difference is where
;; the :while is placed, but it makes a significant difference in the
;; behavior.

;; When x=2 y=1 is reached, :while (<= x y) evaluates false, so all
;; further items in the y collection are skipped.  When x=3 y=1 is
;; reached, the same thing happens.

user=> (for [x [1 2 3]
             y [1 2 3]
             :while (<= x y)
             z [1 2 3]]
         [x y z])
([1 1 1] [1 1 2] [1 1 3]
 [1 2 1] [1 2 2] [1 2 3]
 [1 3 1] [1 3 2] [1 3 3])

;; This is different.  When x=2 y=1 z=1 is reached, :while (<= x y)
;; evaluates false, but since the :while is after the binding for z,
;; all further items in the z collection are skipped.  Then x=2 y=2
;; z=1 is tried, where the while expresssion evaluates true.

user=> (for [x [1 2 3]
             y [1 2 3]
             z [1 2 3]
             :while (<= x y)]
         [x y z])
([1 1 1] [1 1 2] [1 1 3]
 [1 2 1] [1 2 2] [1 2 3]
 [1 3 1] [1 3 2] [1 3 3]
 [2 2 1] [2 2 2] [2 2 3]
 [2 3 1] [2 3 2] [2 3 3]
 [3 3 1] [3 3 2] [3 3 3])
{% endraw %}
{% endhighlight %}


### Example 10
[permalink](#example-10)

{% highlight clojure linenos %}
{% raw %}
(defn all-files-present?
"Takes a list of real file names, and returns a map of files present 1
and not present 0."
[file-seq]
(for [fnam file-seq
 :let [stat-map {(keyword fnam) (look-for fnam "f")}]]
  stat-map))

(into {}  (all-files-present? '("Makefile" "build.sh" "real-estate.csv")))

{:Makefile 1, :build.sh 1, :real-estate.csv 0}{% endraw %}
{% endhighlight %}


