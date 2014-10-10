user=> (when-let [a 2] (+ a a)) ; 2 is a truthy value so the body is evaluated
4
user=> (when-let [a nil] (+ a a)) ; nil is falsey so (+ nil nil) - which throws a null pointer -  is not evaluated
nil
