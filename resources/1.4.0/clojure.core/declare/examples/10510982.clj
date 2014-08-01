user=> (declare show)
#'user/show
user=> (defn welcome [user-name] (prn (show) user-name))
#'user/welcome
user=> (defn show [] (prn "welcome "))
#'user/show
user=> (welcome "lu4nx")
"welcome "
nil "lu4nx"
nil
user=> 