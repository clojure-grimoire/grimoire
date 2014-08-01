;; Creating an empty array defaults to Object[]
user=> (into-array [])
#<Object[] [Ljava.lang.Object;@21f1151f>

;; However, the type of an empty array can be coerced
user=> (into-array String [])
#<String[] [Ljava.lang.String;@578baf24>
