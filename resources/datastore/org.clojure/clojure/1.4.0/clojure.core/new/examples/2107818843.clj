;; Create another ArrayList and add integers using the doto macro
user=> (def ai (doto (new java.util.ArrayList) (.add 1) (.add 2) (.add 0)))
#'user/ai
user=> ai
#<ArrayList [1, 2, 0]>