; a hash map can be stored in a var by using `def`
user=> (def person {:name "Steve" :age 24 :salary 7886 :company "Acme"})
#'user/person
user=> person
{:age 24, :name "Steve", :salary 7886, :company "Acme"}