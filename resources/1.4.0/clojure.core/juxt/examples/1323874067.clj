;; "Explode" a value.

user=> ((juxt identity name) :keyword)
[:keyword "keyword"]


;; eg. to create a map:

user=> (into {} (map (juxt identity name) [:a :b :c :d]))
{:a "a" :b "b" :c "c" :d "d"}
