(:require [clojure.string :as cstr])

(def legal-ref "1321-61")

(cstr/split legal-ref #"-")
["1321" "61"]