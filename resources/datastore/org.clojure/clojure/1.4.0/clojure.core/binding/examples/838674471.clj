;; Here are the definitions.
(defn mymax [x y]
  (min x y))

(defn find-max [x y]
  (max x y))

user=> (let [max mymax]
         (find-max 10 20))

20 ;let is ineffective outside current lexical scope


user=> (binding [max mymax]
         (find-max 10 20))

10 ;because max is now acting as min