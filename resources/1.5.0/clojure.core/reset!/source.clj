(defn reset!
  "Sets the value of atom to newval without regard for the
  current value. Returns newval."
  {:added "1.0"
   :static true}
  [^clojure.lang.Atom atom newval] (.reset atom newval))