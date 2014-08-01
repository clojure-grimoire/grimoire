;; This simple example shows each element of the first relation joined
;; with each element of the second (because they have no columns in common):

user=> (def first-relation #{ {:a 1} {:a 2} })
user=> (def second-relation #{ {:b 1} {:b 2} })
user=> (join first-relation second-relation)
#{{:b 1, :a 1} 
  {:b 2, :a 1} 
  {:b 1, :a 2} 
  {:b 2, :a 2}}


;; Here's a larger example, in which a relation mainly about animal ownership
;; is joined with a relation about animal personality. The join is used to 
;; produce a relation joining information about an animal's personality to 
;; that animal.

user=> (def animals #{{:name "betsy" :owner "brian" :kind "cow"}
                      {:name "jake"  :owner "brian" :kind "horse"}
                      {:name "josie" :owner "dawn"  :kind "cow"}})

user=> (def personalities #{{:kind "cow" :personality "stoic"}
                            {:kind "horse" :personality "skittish"}})
#'user/personalities
user=> (join animals personalities)

#{{:owner "dawn",  :name "josie", :kind "cow",   :personality "stoic"}
  {:owner "brian", :name "betsy", :kind "cow",   :personality "stoic"}
  {:owner "brian", :name "jake",  :kind "horse", :personality "skittish"}}


;; (If cows had two personalities, instead of one, each cow would have 
;; two rows in the output.)

;; Suppose `personalities` used `:species` instead of `:kind`:

user=>  (def personalities #{{:species "cow" :personality "stoic"}
                             {:species "horse" :personality "skittish"}})


;; A simple join would produce results like this:

user=> (join animals personalities)
#{{:kind "horse", :owner "brian", :name "jake", :species "cow", :personality "stoic"}
  {:kind "cow", :owner "dawn", :name "josie", :species "cow", :personality "stoic"}
  {:kind "horse", :owner "brian", :name "jake", :species "horse", :personality "skittish"}
  {:kind "cow", :owner "brian", :name "betsy", :species "cow", :personality "stoic"}
  {:kind "cow", :owner "dawn", :name "josie", :species "horse", :personality "skittish"}
  {:kind "cow", :owner "brian", :name "betsy", :species "horse", :personality "skittish"}}


;; Notice that "Jake" is both a horse and a cow in the first line. That's 
;; likely not what you want. You can tell `join` to only produce output 
;; where the `:kind` value is the same as the `:species` value like this:

user=> (join animals personalities {:kind :species})
#{{:kind "cow", :owner "dawn", :name "josie", :species "cow", :personality "stoic"}
  {:kind "horse", :owner "brian", :name "jake", :species "horse", :personality "skittish"}
  {:kind "cow", :owner "brian", :name "betsy", :species "cow", :personality "stoic"}}


;; Notice that the `:kind` and `:species` keys both appear in each output map.

