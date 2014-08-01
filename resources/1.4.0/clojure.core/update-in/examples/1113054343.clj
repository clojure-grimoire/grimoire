user=> (def users [{:name "James" :age 26}  {:name "John" :age 43}])
#'user/users

user=> (update-in users [1 :age] inc)

[{:name "James", :age 26} {:name "John", :age 44}]
