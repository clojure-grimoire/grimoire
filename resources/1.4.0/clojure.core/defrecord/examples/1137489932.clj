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
-> "foo-bar"