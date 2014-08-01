
user=> (ns test)
nil

test=> (defn- foo []
         "World!")
#'test/foo

test=> (defn bar []
       (str "Hello " (foo)))
#'test/bar

test=> (foo)
"World!"
test=> (bar)
"Hello World!"
test=> (ns playground)
nil
playground=> (test/bar)
"Hello World!"

;; Error will be thrown
;; var: #'test/foo is not public
playground=> (test/foo)
