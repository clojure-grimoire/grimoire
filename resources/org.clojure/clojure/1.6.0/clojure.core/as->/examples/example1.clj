user=> (as-> [] x
             (conj x :a)
             (if :nested
               (conj x :b)
               (conj x :d))
             (conj x :c))
;; [:a :b :c]

;; ported from a draft of Rich Hickey (https://gist.github.com/richhickey/3885504)
