user=&gt; (dissoc {:a 1 :b 2 :c 3}) ; dissoc nothing 
{:a 1, :b 2, :c 3} 

user=&gt; (dissoc {:a 1 :b 2 :c 3} :b) ; dissoc key :b
{:a 1, :c 3} 

user=&gt; (dissoc {:a 1 :b 2 :c 3} :d) ; dissoc not existed key
{:a 1, :b 2, :c 3} 

user=&gt; (dissoc {:a 1 :b 2 :c 3} :c :b) ; several keys at once
{:a 1} 

