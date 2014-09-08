;; create hash map the long way
user=> (hash-map)
{}

;; create hash map the short way
user=> {}
{}

;; sending a key more times, will remap it to the last value
user=> (hash-map :key1 1, :key1 2) 
{:key1 2} 

user=> {:key1 1, :key1 2}
IllegalArgumentException Duplicate key: :key1  clojure.lang.PersistentArrayMap.createWithCheck (PersistentArrayMap.java:70)


user=> (hash-map :key1 'val1, 'key2 :val2, [:compound :key] nil)
{[:compound :key] nil, :key1 val1, key2 :val2} 

