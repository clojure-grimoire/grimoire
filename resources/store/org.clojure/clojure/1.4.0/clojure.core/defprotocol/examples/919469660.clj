;; Protocols allow you to add new abstractions to existing types in a clean way.
;; Polymorphic functions are created in namespaces as opposed to
;; having the polymorphism live on Classes as typically done in OO.

;; example from: 
;; https://speakerdeck.com/bmabey/clojure-plain-and-simple?slide=230
(ns abstraction-a)

(defprotocol AbstractionA
  (foo [obj]))

(extend-protocol AbstractionA
  nil
  (foo [s] (str "foo-A!"))
  String
  (foo [s] (str "foo-A-" (.toUpperCase s))))

(ns abstraction-b)

(defprotocol AbstractionB
  (foo [obj]))

(extend-protocol AbstractionB
  nil
  (foo [s] (str "foo-B!"))
  String
  (foo [s] (str "foo-B-" (.toLowerCase s))))


user=> (require '[abstraction-a :as a])

user=> (require '[abstraction-b :as b])

user=> (a/foo "Bar")
"foo-A-BAR"

user=> (b/foo "Bar")
"foo-B-bar"

user=> (a/foo nil)
"foo-A!"

user=> (b/foo nil)
"foo-B!"
