(use '[clojure.set :only [superset?]])

user=> (superset? #{0} #{0})
true

user=> (superset? #{0 1} #{0})
true

user=> (superset? #{0} #{0 1}) 
false
