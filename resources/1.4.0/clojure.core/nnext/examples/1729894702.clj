user=&gt; (nnext '(1 2 3))
(3)


user=&gt; (nnext [])
nil 


user=&gt; (nnext ['(a b c) '(b a c) '(c b a) '(a c b)])
((c b a) (a c b)) 


user=&gt; (nnext {:a 1, :b 2, :c 3, :d 4})
([:c 3] [:d 4]) 


user=&gt; (nnext #{:a :b :c})
(:c)

