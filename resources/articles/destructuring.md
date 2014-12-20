Clojure Destructuring Tutorial and Cheat Sheet
==============================================

([Source blog post](http://john2x.com/blog/clojure-destructuring) with thanks to John2x for contributing this piece to Grimoire)

Simply put, destructuring in Clojure is a way extract values from a datastructure and bind them to symbols, without having to explicitly traverse the datstructure. It allows for elegant and concise Clojure code.

Vectors
-------

**Syntax:** `[symbol another-symbol] ["value" "another-value"]`

```clojure
(def my-vector [:a :b :c :d])
(def my-nested-vector [:a :b :c :d [:x :y :z]])

(let [[a b c d] my-vector]
  (println a b c d))
;; => :a :b :c :d

(let [[a _ _ d [x y z]] my-nested-vector]
  (println a d x y z))
;; => :a :d :x :y :z
```

You don't have to match the full vector.

```clojure
(let [[a b c] my-vector]
  (println a b c))
;; => :a :b :c
```

You can use `& the-rest` to bind the remaining part of the vector to `the-rest`. 

```clojure
(let [[a b & the-rest] my-vector]
  (println a b the-rest))
;; => :a :b (:c :d)
```

When a destructuring form "exceeds" a vector (i.e. there not enough items in the vector to bind to), the excess symbols will be bound to `nil`.

```clojure
(let [[a b c d e f g] my-vector]
  (println a b c d e f g))
;; => :a :b :c :d nil nil nil
```

You can use `:as some-symbol` as the *last two items* in the destructuring form to bind the whole vector to `some-symbol`

```clojure
(let [[:as all] my-vector]
  (println all))
;; => [:a :b :c :d]

(let [[a :as all] my-vector]
  (println a all))
;; => :a [:a :b :c :d]

(let [[a _ _ _ [x y z :as nested] :as all] my-nested-vector]
  (println a x y z nested all))
;; => :a :x :y :z [:x :y :z] [:a :b :c :d [:x :y :z]]
```

You can use both `& the-rest` and `:as some-symbol`.

```clojure
(let [[a b & the-rest :as all] my-vector]
  (println a b the-rest all))
;; => :a :b (:c :d) [:a :b :c :d]
```

### Optional arguments for functions

With destructuring and the `& the-rest` form, you can specify optional arguments to functions.

```clojure
(defn foo [a b & more-args]
  (println a b more-args))
(foo :a :b) ;; => :a :b nil
(foo :a :b :x) ;; => :a :b (:x)
(foo :a :b :x :y :z) ;; => :a :b (:x :y :z)

(defn foo [a b & [x y z]]
  (println a b x y z))
(foo :a :b) ;; => :a :b nil nil nil
(foo :a :b :x) ;; => :a :b :x nil nil
(foo :a :b :x :y :z) ;; => :a :b :x :y :z
```

Maps
----

**Syntax:** `{symbol :key, another-symbol :another-key} {:key "value" :another-key "another-value"}`

```clojure
(def my-hashmap {:a "A" :b "B" :c "C" :d "D"})
(def my-nested-hashmap {:a "A" :b "B" :c "C" :d "D" :q {:x "X" :y "Y" :z "Z"}})

(let [{a :a d :d} my-hashmap]
  (println a d))
;; => A D

(let [{a :a, b :b, {x :x, y :y} :q} my-nested-hashmap]
  (println a b x y))
;; => A B X Y
```

Similar to vectors, if a key is not found in the map, the symbol will be bound to `nil`.

```clojure
(let [{a :a, not-found :not-found, b :b} my-hashmap]
  (println a not-found b))
;; => A nil B
```

You can provide an optional default value for these missing keys with the `:or` keyword and a map of default values.

```clojure
(let [{a :a, not-found :not-found, b :b, :or {not-found ":)"}} my-hashmap]
  (println a not-found b))
;; => A :) B
```

The `:as some-symbol` form is also available for maps, but unlike vectors it can be specified anywhere (but still preferred to be the last two pairs).

```clojure
(let [{a :a, b :b, :as all} my-hashmap]
  (println a b all))
;; => A B {:a A :b B :c C :d D}
```

And combining `:as` and `:or` keywords (again, `:as` preferred to be the last).

```clojure
(let [{a :a, b :b, not-found :not-found, :or {not-found ":)"}, :as all} my-hashmap]
  (println a b not-found all))
;; => A B :) {:a A :b B :c C :d D}
```

There is no `& the-rest` for maps.

### Shortcuts

Having to specify `{symbol :symbol}` for each key is repetitive and verbose (it's almost always going to be the symbol equivalent of the key), so shortcuts are provided so you only have to type the symbol once.

Here are all the previous examples using the `:keys` keyword followed by a vector of symbols:

```clojure
(let [{:keys [a d]} my-hashmap]
  (println a d))
;; => A D

(let [{:keys [a b], {:keys [x y]} :q} my-nested-hashmap]
  (println a b x y))
;; => A B X Y

(let [{:keys [a not-found b]} my-hashmap]
  (println a not-found b))
;; => A nil B

(let [{:keys [a not-found b], :or {not-found ":)"}} my-hashmap]
  (println a not-found b))
;; => A :) B

(let [{:keys [a b], :as all} my-hashmap]
  (println a b all))
;; => A B {:a A :b B :c C :d D}

(let [{:keys [a b not-found], :or {not-found ":)"}, :as all} my-hashmap]
  (println a b not-found all))
;; => A B :) {:a A :b B :c C :d D}
```

There are also `:strs` and `:syms` alternatives, for when your map has strings or symbols for keys (instead of keywords), respectively.

```clojure
(let [{:strs [a d]} {"a" "A", "b" "B", "c" "C", "d" "D"}]
  (println a d))
;; => A D

(let [{:syms [a d]} {'a "A", 'b "B", 'c "C", 'd "D"}]
  (println a d))
;; => A D
```

### Keyword arguments for function

Map destructuring also works with lists (but not vectors).

```clojure
(let [{:keys [a b]} '("X", "Y", :a "A", :b "B")]
(println a b))
;; => A B
```

This allows your functions to have optional keyword arguments.

```clojure
(defn foo [a b & {:keys [x y]}]
  (println a b x y))
(foo "A" "B")  ;; => A B nil nil
(foo "A" "B" :x "X")  ;; => A B X nil
(foo "A" "B" :x "X" :y "Y")  ;; => A B X Y
```

More examples
=============

Here be dragons.

**TODO**
