### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(defprotocol Fly
  "A simple protocol for flying"
  (fly [this] "Method to fly"))

(defrecord Bird [name species]
  Fly
  (fly [this] (str (:name this) " flies...")))

(extends? Fly Bird)
-> true

(def crow (Bird. "Crow" "Corvus corax"))

(fly crow)
-> "Crow flies..."{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; from Stuart Halloway's examples:

(defprotocol Player
  (choose [p])
  (update-strategy [p me you]))

(defrecord Stubborn [choice]
  Player
  (choose [_] choice)
  (update-strategy [this _ _] this))

(defrecord Mean [last-winner]
  Player
  (choose [_]
          (if last-winner
            last-winner
            (random-choice)))
  (update-strategy [_ me you]
                   (->Mean (when (iwon? me you) me))))
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; defprotocol does NOT support interfaces with variable argument lists,
;; like [this & args]
;; (this is not documented anywhere... )

;; The workaround is to define the interface with the variable arg list in a fn
;; separately outside of the protocol, which then calls the protocol interface
;; with a slightly different name and an array in place of the variable list,
;; like:

(defprotocol MyProtocol
  (-my-fn [this args]))

(defn my-fn [this & args] (-my-fn this args)){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
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
{% endraw %}
{% endhighlight %}


