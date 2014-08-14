(use 'clojure.data)
(def uno {:same "same", :different "one"})
(def dos {:same "same", :different "two", :onlyhere "whatever"})
(diff uno dos)
=> ({:different "one"} {:onlyhere "whatever", :different "two"} {:same "same"})
;;  {different in uno} {     different or unique in dos       } {same in both}
(diff {:a 1} {:a 1 :b 2})
=> (nil {:b 2} {:a 1})
;; the first contains nothing unique, but only the second contains :b
;; and both contain :a