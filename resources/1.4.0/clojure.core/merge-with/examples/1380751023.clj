user=> (merge-with concat
		  {"Lisp" ["Common Lisp" "Clojure"]
		   "ML" ["Caml" "Objective Caml"]}
		  {"Lisp" ["Scheme"]
		   "ML" ["Standard ML"]})
{"Lisp" ("Common Lisp" "Clojure" "Scheme"), "ML" ("Caml" "Objective Caml" "Standard ML")}

user=> (clojure.pprint/pp)
{"Lisp" ("Common Lisp" "Clojure" "Scheme"), "ML" ("Caml" "Objective Caml" "Standard ML")}
nil
