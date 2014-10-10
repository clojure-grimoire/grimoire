user=> (defprotocol P (id [this]))
P
user=> (extend-protocol P 
         String 
         (id [this] this)
         clojure.lang.Symbol 
         (id [this] (name this))
         clojure.lang.Keyword
         (id [this] (name this)))
nil
user=> (extenders P)
(java.lang.String clojure.lang.Symbol clojure.lang.Keyword)
