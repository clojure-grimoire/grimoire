;; Destructuring with a vector, inside a "let" form, more complex cases:

user=> (let [[first-element second-element & the-rest] my-vector] 
         (str "first=" first-element " second=" second-element " 
           the-rest=" the-rest))
"first=1 second=2 the-rest=(3 4)"
;; notice how "the-rest" is a sequence

user=> (let [[first-element second-element third-element fourth-element 
               :as everything] 
             my-vector] 
         (str "first=" first-element " second=" second-element " 
           third=" third-element " fourth=" fourth-element " 
           everything=" everything))
"first=1 second=2 third=3 fourth=4 everything=[1 2 3 4]"
;; notice how "everything" is the whole vector