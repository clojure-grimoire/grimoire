user=> (use 'clojure.pprint 'clojure.reflect)
nil
user=> (def x (:members (reflect clojure.lang.BigInt)))
#'user/x
user=> (print-table [:name :type :flags] (sort-by :name x))
======================================================================
:name               | :type                | :flags                   
======================================================================
ONE                 | clojure.lang.BigInt  | #{:static :public :final}
ZERO                | clojure.lang.BigInt  | #{:static :public :final}
add                 |                      | #{:public}               
bipart              | java.math.BigInteger | #{:public :final}        
bitLength           |                      | #{:public}               
byteValue           |                      | #{:public}               
clojure.lang.BigInt |                      | #{:private}              
doubleValue         |                      | #{:public}               
equals              |                      | #{:public}               
floatValue          |                      | #{:public}               
fromBigInteger      |                      | #{:static :public}       
fromLong            |                      | #{:static :public}       
hashCode            |                      | #{:public}               
intValue            |                      | #{:public}               
longValue           |                      | #{:public}               
lpart               | long                 | #{:public :final}        
lt                  |                      | #{:public}               
multiply            |                      | #{:public}               
quotient            |                      | #{:public}               
remainder           |                      | #{:public}               
shortValue          |                      | #{:public}               
toBigInteger        |                      | #{:public}               
toString            |                      | #{:public}               
valueOf             |                      | #{:static :public}       
======================================================================
nil
