### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Some clojure.zip functions will overwrite clojure.core's definitions
(use 'clojure.zip)
;;=> WARNING: next already refers to: #'clojure.core/next in namespace: user, being replaced by: #'clojure.zip/next
;;=> WARNING: replace already refers to: #'clojure.core/replace in namespace: user, being replaced by: #'clojure.zip/replace
;;=> WARNING: remove already refers to: #'clojure.core/remove in namespace: user, being replaced by: #'clojure.zip/remove

;; You may wish to require :as in order to avoid the above
(require '[clojure.zip :as z])

;; For the purposes of keeping the examples that follow clean,
;; assume we have taken the former route: (use 'clojure.zip)
user> (def z [[1 2 3] [4 [5 6] 7] [8 9]])
#'user/z

user> (def zp (zipper vector? seq (fn [_ c] c) z))
#'user/zp

user> zp
[[[1 2 3] [4 [5 6] 7] [8 9]] nil]

user> (-> zp down)
[[1 2 3] {:l [], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]]], :ppath nil, :r ([4 [5 6] 7] [8 9])}]

user> (first (-> zp down))
[1 2 3]

user> (-> zp down right)
[[4 [5 6] 7] {:l [[1 2 3]], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]]], :ppath nil, :r ([8 9])}]

user> (first (-> zp down right))
[4 [5 6] 7]

user> (-> zp down right down right)
[[5 6] {:l [4], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]] [4 [5 6] 7]], :ppath {:l [[1 2 3]], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]]], :ppath nil, :r ([8 9])}, :r (7)}]

user> (first (-> zp down right down right))
[5 6]

user> (-> zp down right down right down right)
[6 {:l [5], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]] [4 [5 6] 7] [5 6]], :ppath {:l [4], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]] [4 [5 6] 7]], :ppath {:l [[1 2 3]], :pnodes [[[1 2 3] [4 [5 6] 7] [8 9]]], :ppath nil, :r ([8 9])}, :r (7)}, :r nil}]

user> (first (-> zp down right down right down right))
6{% endraw %}
{% endhighlight %}


