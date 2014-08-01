;; Destructuring with a vector, inside a "let" form, a simple case (a symbol
;; for each element):

;; destructuring with an inline vector
user=> (let [[first-element second-element third-element fourth-element] 
             [10 20 30 40]] 
         (str "first=" first-element " second=" second-element 
           " third=" third-element " fourth=" fourth-element))
"first=10 second=20 third=30 fourth=40"
;; notice how 4 symbols were created pointing to the scalars 10, 20, 30 and 40


;; destructuring with a symbol to a vector
user=> (def my-vector [1 2 3 4])
#'user/my-vector

user=> (let [[first-element second-element third-element fourth-element] my-vector] 
         (str "first=" first-element " second=" second-element 
           " third=" third-element " fourth=" fourth-element))
"first=1 second=2 third=3 fourth=4"