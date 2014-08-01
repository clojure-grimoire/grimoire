;; from Stu's examples:

(defrecord Person [fname lname address])
-> user.Person

(defrecord Address [street city state zip])
-> user.Address

(def stu (Person. "Stu" "Halloway"
           (Address. "200 N Mangum"
                      "Durham"
                      "NC"
                      27701)))
-> #'user/stu

(:lname stu)
-> "Halloway"

(-> stu :address :city)
-> "Durham"

(assoc stu :fname "Stuart")
-> #:user.Person{:fname "Stuart", :lname "Halloway", :address #:user.Address{:street "200 N Mangum", :city "Durham", :state "NC", :zip 27701}}

(update-in stu [:address :zip] inc)
-> #:user.Person{:fname "Stu", :lname "Halloway", :address #:user.Address{:street "200 N Mangum", :city "Durham", :state "NC", :zip 27702}}