(require '[clojure.zip :as z])

user=> (z/right
        (z/down
         (z/xml-zip 
          {:tag :root :content [{:tag :any :content ["foo" "bar"]} "bar"]})))
["bar" {:l [{:content ["foo" "bar"], :tag :any}], :pnodes [{:content [{:content ["foo" "bar"], :tag :any} "bar"], :tag :root}], :ppath nil, :r nil}]

;; The above can also be written like this:
user=> (->
        (z/xml-zip {:tag :root :content [{:tag :any :content ["foo" "bar"]} "bar"]})
        z/down z/right)
["bar" {:l [{:content ["foo" "bar"], :tag :any}], :pnodes [{:content [{:content ["foo" "bar"], :tag :any} "bar"], :tag :root}], :ppath nil, :r nil}]