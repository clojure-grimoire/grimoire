user=> (macroexpand-1 '(defstruct mystruct[a b]))
(def mystruct (clojure.core/create-struct [a b]))
