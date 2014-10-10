user=> (cond-> []
               (< 2 3) (conj :a)
               42 (conj :b)
               (> 2 3) (conj :nope)
               :go (conj :more))
;; [:a :b :more]

;; ported from a draft of Rich Hickey (https://gist.github.com/richhickey/3885504)
