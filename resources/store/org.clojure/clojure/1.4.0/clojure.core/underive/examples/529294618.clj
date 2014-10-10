;; create a simple hierarchy using the global hierarchy
;; and demonstrate how underive is used

user=> (derive ::dog ::animal)
nil
user=> (derive ::spaniel ::dog)
nil
user=> (derive ::tabby ::dog)
nil
user=> (ancestors ::tabby)
#{:user/dog :user/animal}
user=> (underive ::tabby ::dog)
nil
user=> (ancestors ::tabby)
nil
user=>