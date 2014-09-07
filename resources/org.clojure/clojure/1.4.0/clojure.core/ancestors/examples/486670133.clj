;; make up a hierarchy a beagle is a sporting breed is a dog is a quadraped is an 
;; animal

user=> (derive ::quadruped ::animal)
nil
user=> (derive ::dog ::quadruped)
nil
user=> (derive ::sporting_breed ::dog)
nil
user=> (derive ::beagle ::sporting_breed)
nil
user=> (ancestors ::beagle)
#{:user/dog :user/sporting_breed :user/animal :user/quadruped}
user=>