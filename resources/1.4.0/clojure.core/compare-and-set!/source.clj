(defn compare-and-set!
  "Atomically sets the value of atom to newval if and only if the
  current value of the atom is identical to oldval. Returns true if
  set happened, else false"
  {:added "1.0"
   :static true}
  [^clojure.lang.Atom atom oldval newval] (.compareAndSet atom oldval newval))