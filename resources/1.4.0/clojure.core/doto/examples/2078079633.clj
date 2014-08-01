;; quick demonstration of using a Collections function on the resulting ArrayList

user=> (def al (doto (java.util.ArrayList.) (.add 11) (.add 3)(.add 7)))
#'user/al
user=> al
#<ArrayList [11, 3, 7]>
user=> (java.util.Collections/sort al)
nil
user=> al
#<ArrayList [3, 7, 11]>
user=>