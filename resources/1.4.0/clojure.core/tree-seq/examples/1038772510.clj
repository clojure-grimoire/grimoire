user=> (tree-seq map? #(interleave (keys %) (vals %)) {:a 1 :b {:c 3 :d 4 :e {:f 6 :g 7}}})

({:a 1, :b {:c 3, :d 4, :e {:f 6, :g 7}}} :a 1 :b {:c 3, :d 4, :e {:f 6, :g 7}} :c 3 :d 4 :e {:f 6, :g 7} :f 6 :g 7)