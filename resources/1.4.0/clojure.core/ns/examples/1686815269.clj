;; This example will illustrate changing between namespaces at the repl

;; At the repl, the ns macro can be used to create a namespace, but it is used to change the current namespace (be careful of typos)
user=>(ns demo.namespace)
nil
demo.namespace=> ; The prompt at the repl is now "demo.namespace" reflecting that the current namespace is no longer "user".

;; Add a new function to demo.namespace
demo.namespace=>(defn foo [] (prn "Hello from demo.namespace"))
#'demo.namespace/foo

;; From within "demo.namespace" we can use foo without qualifying it
demo.namespace=>(foo)
"Hello from demo.namespace"
nil

;; Switch back to the "user" namespace
demo.namespace=>(ns user)
nil

;; We can no longer use "foo" without qualification
user=> (foo)
java.lang.Exception: Unable to resolve symbol: foo in this context (NO_SOURCE_FILE:4)

user=> (demo.namespace/foo)
"Hello from demo.namespace"
nil

;; The public symbols of "demo.namespace" can be "referred into" the "user" namespace if desired
user=> (refer 'demo.namespace)
nil

;; foo is now an alias in the "user" namespace which refers to the "demo.namespace/foo" symbol
user=> (foo)
"Hello from demo.namespace"
nil