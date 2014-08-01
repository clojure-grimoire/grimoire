;; You need to be careful about key collisions.  You probably shouldn't 
;; depend on the exact behavior.
user=> (rename-keys {:a 1 :b 2} {:a :b})
{:b 1}

user=> (rename-keys  {:a 1 :b 2}  {:a :b :b :a})
{:a 1}

;; You can work around key collisions by using an array-map to control
;; the order of the renamings.
user=> (rename-keys  {:a 1 :b 2 :c 3}  (array-map :a :tmp :b :a :tmp :b))
{:b 1, :a 2, :c 3}
