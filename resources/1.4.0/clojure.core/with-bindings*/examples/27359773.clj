user=> (let [f (fn [] *warn-on-reflection*)]
         (with-bindings* {#'*warn-on-reflection* true} f))
true