;; ns-map = ns-refers + ns-interns + ns-imports
user=> (count (ns-imports *ns*))
;;=> 96

user=> (count (ns-interns *ns*))
;;=> 2

user=> (count (ns-refers *ns*))
;;=> 590

user=> (+ *1 *2 *3)
;;=> 688

user=> (count (ns-map *ns*))
;;=> 688