;; be careful, with-redefs can permanently change a var if applied concurrently:

user> (defn ten [] 10)
#'user/ten
user> (doall (pmap #(with-redefs [ten (fn [] %)] (ten)) (range 20 100)))
...
user> (ten)
79