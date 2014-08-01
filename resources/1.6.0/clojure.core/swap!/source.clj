(defn swap!
  "Atomically swaps the value of atom to be:
  (apply f current-value-of-atom args). Note that f may be called
  multiple times, and thus should be free of side effects.  Returns
  the value that was swapped in."
  {:added "1.0"
   :static true}
  ([^clojure.lang.Atom atom f] (.swap atom f))
  ([^clojure.lang.Atom atom f x] (.swap atom f x))
  ([^clojure.lang.Atom atom f x y] (.swap atom f x y))
  ([^clojure.lang.Atom atom f x y & args] (.swap atom f x y args)))