;; The map->Recordclass form works only in Clojure 1.3 or higher

(defrecord Foo [a b])

(defrecord Bar [a b c])

(defrecord Baz [a c])

(def f (Foo. 10 20))
(println f)
-> #user.Foo{:a 10, :b 20}

(def r (map->Bar (merge f {:c 30})))
(println r)
-> #user.Bar{:a 10, :b 20, :c 30}

(def z (map->Baz (merge f {:c 30})))
(println z)
-> #user.Baz{:a 10, :c 30, :b 20}