user=> (rename-keys {:a 1, :b 2} {:a :new-a, :b :new-b})
{:new-a 1, :new-b 2}


;; The behavior when the second map contains a key not in the first is interesting.
;; I suspect you shouldn't depend on it. (Clojure 1.1 - no longer happens in 1.2.1)

user=> (rename-keys {:a 1} {:b :new-b})
{ :a 1, :new-b nil}
