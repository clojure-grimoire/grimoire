user=> (array-map :a 10)
{:a 10}

user=> (array-map :a 10 :b 20)
{:a 10 :b 20}

user=> (apply array-map [:a 10 :b 20 :c 30])
{:a 10 :b 20 :c 30}

user=> (apply assoc {} [:a 10 :b 20 :c 30]) ;same result using assoc
{:a 10 :b 20 :c 30}
