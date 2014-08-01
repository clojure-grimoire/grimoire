(use '[clojure.set :only (index)])

;; Suppose you have a set of descriptions of the weights of animals:

user=> (def weights #{ {:name 'betsy :weight 1000}
                       {:name 'jake :weight 756}
                       {:name 'shyq :weight 1000} })


;; You want the names of all the animals that weight 1000. One way to do 
;; that uses `index`. First, you can group the set elements (the maps) so
;; that those with the same weights are in the same group.

user=> (def by-weight (index weights [:weight]))
#'user/by-weight

;; index returns a map.  The keys are maps themselves, where {:weight 756} and {:weight 1000} are taken from the maps in the weights set.  The values in the map returned by index are sets that contain map entries from the above weights set.

user=> by-weight
{{:weight 756} #{{:name jake, :weight 756}}, 
 {:weight 1000} #{{:name shyq, :weight 1000} 
                  {:name betsy, :weight 1000}}}


;; To better visualize the by-weight map that is returned by index, you can query it using get, using {:weight 756} as the key.  This will return all the maps (animals) that contain a weight of 756.  In this case, there is only one result, which is a set containing a single map. 

user=> (get by-weight {:weight 756})
#{{:name jake, :weight 756}}


;; To see that there are two animals with a weight of 1000, you can query by-weight with the key {:weight 1000}.  This returns a set containing two maps.

user=> (get by-weight {:weight 1000})
#{{:name shyq, :weight 1000} {:name betsy :weight 1000}}
 

;; You can verify by using count

user=> (count (get by-weight {:weight 1000}))
2


;; To get the names of those two animals we can map a name-extracting function
;; over the set of two maps. Since a keyword in a map is also a function that
;; returns its corresponding value, we can just use `:name` as our function:

user=> (map :name (get by-weight {:weight 1000}))
(shyq betsy)
