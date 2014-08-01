user=> (def p {:name "James" :age 26})
#'user/p

user=> (update-in p [:age] inc)
{:name "James", :age 27}

;; remember, the value of p hasn't changed!
user=> (update-in p [:age] + 10)
{:name "James", :age 36}

