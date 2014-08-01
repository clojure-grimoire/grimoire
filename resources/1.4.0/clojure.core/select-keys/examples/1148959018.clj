user=&gt; (select-keys {:a 1 :b 2} [:a])
{:a 1}
user=&gt; (select-keys {:a 1 :b 2} [:a :c])
{:a 1}
