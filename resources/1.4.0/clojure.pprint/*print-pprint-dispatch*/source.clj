(defonce ^:dynamic ; If folks have added stuff here, don't overwrite
 ^{:doc "The pretty print dispatch function. Use with-pprint-dispatch or set-pprint-dispatch
to modify.",
   :added "1.2"}
 *print-pprint-dispatch* nil)