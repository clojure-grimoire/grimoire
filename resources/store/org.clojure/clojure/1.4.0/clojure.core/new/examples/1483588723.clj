;; Create a Java ArrayList using the 0 argument constructor
user=> (def a  (new java.util.ArrayList))
#'user/a
user=> (.add a "aaa")
true
user=> (.add a "bbb")
true
user=> a
#<ArrayList [aaa, bbb]>
