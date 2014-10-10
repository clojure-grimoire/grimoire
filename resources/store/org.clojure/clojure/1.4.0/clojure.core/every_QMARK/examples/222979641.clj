;; this is kind of weird IMO... but it works that way (the same for vectors)
user=> (every? true? '())
true
user=> (every? false? '())
true