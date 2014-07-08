### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; from Stu's examples:

(defrecord Person [fname lname address])
-> user.Person

(defrecord Address [street city state zip])
-> user.Address

(def stu (Person. "Stu" "Halloway"
           (Address. "200 N Mangum"
                      "Durham"
                      "NC"
                      27701)))
-> #'user/stu

(:lname stu)
-> "Halloway"

(-> stu :address :city)
-> "Durham"

(assoc stu :fname "Stuart")
-> #:user.Person{:fname "Stuart", :lname "Halloway", :address #:user.Address{:street "200 N Mangum", :city "Durham", :state "NC", :zip 27701}}

(update-in stu [:address :zip] inc)
-> #:user.Person{:fname "Stu", :lname "Halloway", :address #:user.Address{:street "200 N Mangum", :city "Durham", :state "NC", :zip 27702}}{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; This example shows how to implement a Java interface in defrecord.
;; We'll implement FileNameMap (because it has a simple interface,
;; not for its real purpose).

(import java.net.FileNameMap)
-> java.net.FileNameMap

;; Define a record named Thing with a single field a.  Implement
;; FileNameMap interface and provide an implementation for the single
;; method:  String getContentTypeFor(String fileName)
(defrecord Thing [a]
  FileNameMap
    (getContentTypeFor [this fileName] (str a "-" fileName)))
-> user.Thing

;; construct an instance of the record
(def thing (Thing. "foo"))
-> #'user/thing

;; check that the instance implements the interface
(instance? FileNameMap thing)
-> true

;; get all the interfaces for the record type
(map #(println %) (.getInterfaces Thing))
-> java.net.FileNameMap
-> clojure.lang.IObj
-> clojure.lang.ILookup
-> clojure.lang.IKeywordLookup
-> clojure.lang.IPersistentMap
-> java.util.Map
-> java.io.Serializable

;; actually call the method on the thing instance and pass "bar"
(.getContentTypeFor thing "bar")
-> "foo-bar"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; prepare a protocol
user=> (defprotocol Fun-Time (drinky-drinky [_]))
Fun-Time

;; define a record and extend the previous protocol, implementing its function
user=> (defrecord Someone [nick-name preffered-drink] Fun-Time (drinky-drinky [_] (str nick-name "(having " preffered-drink "): uuumm")))
user.Someone
;; NOTE how 'nick-name' and 'preffered-drink' are symbols that are not declared anywhere, they are 'provided' inside the function

;; instantiate the protocol once and store it
user=> (def dude (->Someone "belun" "daiquiri"))
#'user/dude

;; use the function defined inside the protocol on the protocol instance
user=> (drinky-drinky dude)
"belun(having daiquiri): uuumm"


;; courtessy of Howard Lewis Ship - http://java.dzone.com/articles/changes-cascade-and-cautionary{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
; If you define a defrecord in one namespace and want to use it
; from another, first require the namespace and then import
; the record as a regular class.
; The require+import order makes sense if you consider that first
; the namespace has to be compiled--which generates a class for
; the record--and then the generated class must be imported.
; (Thanks to raek in #clojure for the explanations!)

; Namespace 1 in "my/data.clj", where a defrecord is declared
(ns my.data)

(defrecord Employee [name surname])


; Namescape 2 in "my/queries.clj", where a defrecord is used
(ns my.queries
  (:require my.data)
  (:import [my.data Employee]))

(println
  "Employees named Albert:"
  (filter #(= "Albert" (.name %))
    [(Employee. "Albert" "Smith")
     (Employee. "John" "Maynard")
     (Employee. "Albert" "Cheng")]))
  {% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
;; The map->Recordclass form works only in Clojure 1.3 or higher

(defrecord Foo [a b])

(defrecord Bar [a b c])

(defrecord Baz [a c])

(def f (Foo. 10 20))
(println f)
-> #user.Foo{:a 10, :b 20}

(def r (map->Bar (merge f {:c 30})))
(println r)
-> #user.Bar{:a 10, :b 20, :c 30}

(def z (map->Baz (merge f {:c 30})))
(println z)
-> #user.Baz{:a 10, :c 30, :b 20}{% endraw %}
{% endhighlight %}


