;; simple example a toy poodle is a poodle is a dog is an animal

user=> (derive ::dog ::animal)
nil
user=> (derive ::poodle ::dog)
nil
user=> (derive ::toy_poodle ::poodle)
nil
user=> (descendants ::animal)
#{:user/toy_poodle :user/poodle :user/dog}
user=>