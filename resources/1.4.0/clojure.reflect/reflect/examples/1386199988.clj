user=> (use 'clojure.reflect 'clojure.pprint)
nil
user=> (def r (reflect *in*))
#'user/r
user=> (count (:members r))
9
user=> (pprint (map class (:members r)))
(clojure.reflect.Constructor
 clojure.reflect.Method
 clojure.reflect.Field
 clojure.reflect.Field
 clojure.reflect.Method
 clojure.reflect.Method
 clojure.reflect.Method
 clojure.reflect.Method
 clojure.reflect.Field)
nil
user=> (pprint r)
{:bases #{java.io.PushbackReader},
 :flags #{:public},
 :members
 #{{:name clojure.lang.LineNumberingPushbackReader,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :parameter-types [java.io.Reader],
    :exception-types [],
    :flags #{:public}}
   {:name read,
    :return-type int,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :parameter-types [],
    :exception-types [java.io.IOException],
    :flags #{:public}}
   {:name _atLineStart,
    :type boolean,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :flags #{:private}}
   {:name newline,
    :type int,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :flags #{:private :static :final}}
   {:name unread,
    :return-type void,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :parameter-types [int],
    :exception-types [java.io.IOException],
    :flags #{:public}}
   {:name readLine,
    :return-type java.lang.String,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :parameter-types [],
    :exception-types [java.io.IOException],
    :flags #{:public}}
   {:name atLineStart,
    :return-type boolean,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :parameter-types [],
    :exception-types [],
    :flags #{:public}}
   {:name getLineNumber,
    :return-type int,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :parameter-types [],
    :exception-types [],
    :flags #{:public}}
   {:name _prev,
    :type boolean,
    :declaring-class clojure.lang.LineNumberingPushbackReader,
    :flags #{:private}}}}
nil
