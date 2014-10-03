user=> (def s #{:a :b :c :d})
#'user/s

;; elements may be added to a set via conj[oin]
user=> (conj s :e)
#{:d :a :b :e :c}

;; sets are seqable, unless the set is sorted no order is guranteed
user=> (seq s)
(:d :a :b :c)

;; sets are seqable, and thus are countable
user=> (count s)
4

;; sets act as the identity function for elements they contain
user=> (s :b)
:b

;; sets return nil when invoked with elements they do not contain
user=> (s :k)
nil
