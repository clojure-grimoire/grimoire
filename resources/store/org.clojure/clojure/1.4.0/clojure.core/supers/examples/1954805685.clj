;;find superclasses and interfaces of Java objects...
user> (supers Object)
nil

user> (supers String)
#{java.lang.Comparable java.lang.CharSequence java.io.Serializable java.lang.Object}

;;...or Java interfaces
user> (supers javax.naming.Name)
#{java.lang.Cloneable java.lang.Comparable java.io.Serializable}

;;Also with clojure types...
user> (defrecord MyThing [a b c])
user.MyThing

user> (supers MyThing)
#{clojure.lang.Counted java.lang.Iterable clojure.lang.IKeywordLookup clojure.lang.IObj clojure.lang.IPersistentMap clojure.lang.Associative clojure.lang.Seqable java.util.Map clojure.lang.IMeta java.io.Serializable java.lang.Object clojure.lang.IPersistentCollection clojure.lang.ILookup}
