;; group by a primary key
user=> (group-by :user-id [{:user-id 1 :uri "/"} 
                           {:user-id 2 :uri "/foo"} 
                           {:user-id 1 :uri "/account"}])

{1 [{:user-id 1, :uri "/"} {:user-id 1, :uri "/account"}],
 2 [{:user-id 2, :uri "/foo"}]}
