user=> (keep-indexed #(if (odd? %1) %2) [:a :b :c :d :e])
(:b :d)