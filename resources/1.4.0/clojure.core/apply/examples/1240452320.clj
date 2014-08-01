;; Here's an example that uses the optional second argument, args:

user=> (apply map vector [[:a :b] [:c :d]])
([:a :c] [:b :d])

;; In this example, 'f' = 'map', 'args' = 'vector', and argseq = '[:a :b] [:c :d]',
;; making the above code equivalent to

user=> (map vector [:a :b] [:c :d])
([:a :c] [:b :d]) ;Same answer as above

;; It might help to think of 'map' and 'vector' "slipping inside" the argument list
;; ( '[[:a :b] [:c :d]]' ) to give '[map vector [:a :b] [:c :d]]' , which then 
;; becomes the executable form '(map vector [:a :b] [:c :d])' .