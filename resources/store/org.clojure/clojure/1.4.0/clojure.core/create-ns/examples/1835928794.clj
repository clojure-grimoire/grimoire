;; Let's create a namespace and check for our result
;; the new namespace will be "my-new-namespace"

;; obviously, it does not exist yet, so looking for it, finds nothing
user=> (find-ns 'my-new-namespace) 
nil

;; let's create it
user=> (create-ns 'my-new-namespace)
#&lt;Namespace my-new-namespace&gt;

;; now searching for it again will have a result
user=> (find-ns 'my-new-namespace)
#&lt;Namespace my-new-namespace&gt;
