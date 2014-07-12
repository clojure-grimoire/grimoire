### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; let is a Clojure special form, a fundamental building block of the language.
;;
;; In addition to parameters passed to functions, let provides a way to create
;; lexical bindings of data structures to symbols. The binding, and therefore
;; the ability to resolve the binding, is available only within the lexical
;; context of the let.
;;
;; let uses pairs in a vector for each binding you'd like to make and the value
;; of the let is the value of the last expression to be evaluated. let also
;; allows for destructuring which is a way to bind symbols to only part of a
;; collection.

;; A basic use for a let:
user=> (let [x 1]
         x)
1

;; Note that the binding for the symbol y won't exist outside of the let:
user=> (let [y 1]
         y)
1
user=> (prn y)
java.lang.Exception: Unable to resolve symbol: y in this context (NO_SOURCE_FILE:7)

;; Another valid use of let:
user=> (let [a 1 b 2]
         (+ a b))
3

;; The forms in the vector can be more complex (this example also uses
;; the thread macro):
user=> (let [c (+ 1 2)
             [d e] [5 6]]
         (-> (+ d e) (- c)))
8

;; The bindings for let need not match up (note the result is a numeric
;; type called a ratio):
user=> (let [[g h] [1 2 3]]
         (/ g h))
1/2

;; From http://clojure-examples.appspot.com/clojure.core/let with permission.{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (let [a (take 5 (range))
             {:keys [b c d] :or {d 10 b 20 c 30}} {:c 50 :d 100}
             [e f g & h] ["a" "b" "c" "d" "e"]
             _ (println "I was here!")
             foo 12
             bar (+ foo 100)]
         [a b c d e f g h foo bar])
I was here!
[(0 1 2 3 4) 20 50 100 "a" "b" "c" ("d" "e") 12 112]
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
; :as example

user=> (let [[x y :as my-point] [5 3]]
         (println x y)
         (println my-point))

5 3
[5 3]

; :as names the group you just destructured.

; equivalent to (and better than)

user=> (let [[x y] [5 3]
             my-point [x y]]
         ;...{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;;; map destructuring, all features
user=>
(let [
      ;;Binding Map
      {:keys [k1 k2]        ;; bind vals with keyword keys
       :strs [s1 s2]        ;; bind vals with string keys
       :syms [sym1 sym2]    ;; bind vals with symbol keys
       :or {k2 :default-kw, ;; default values
            s2 :default-s,
            sym2 :default-sym}
       :as m}  ;; bind the entire map to `m`
      ;;Data
      {:k1 :keyword1, :k2 :keyword2,  ;; keyword keys
       "s1" :string1, "s2" :string2,  ;; string keys
       'sym1 :symbol1,                ;; symbol keys
       ;; 'sym2 :symbol2              ;; `sym2` will get default value
       }]
  [k1 k2 s1 s2 sym1 sym2 m])  ;; return value

[:keyword1, :keyword2,
 :string1, :string2,
 :symbol1, :default-sym, ;; key didn't exist, so got the default
 {'sym1 :symbol1, :k1 :keyword1, :k2 :keyword2,
  "s1" :string1, "s2" :string2}]

;; remember that vector and map destructuring can also be used with
;; other macros that bind variables, e.g. `for` and `doseq`{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;;; no value of a key
user> (let [{:keys [a b] :as m} (:x {})]
        [a b m])
[nil nil nil]

;;; same as above
user> (let [{:keys [a b] :as m} nil]
        [a b m])
[nil nil nil]

;;; similar case on Vector
user> (let [[a b :as v] nil]
        [a b v])
[nil nil nil]
{% endraw %}
{% endhighlight %}


