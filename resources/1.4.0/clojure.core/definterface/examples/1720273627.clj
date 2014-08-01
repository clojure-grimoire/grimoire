;; Part of a definterface from a Clojure program for the n-body problem
;; at the Computer Language Benchmarks Game web site.
;; For the rest of the program using it, see:
;; http://github.com/jafingerhut/clojure-benchmarks/blob/master/nbody/nbody.clj-14.clj

;; Currently Clojure does not permit type hints of arrays, e.g. ^ints as
;; argument types or return types in a definterface.  This may be enhanced
;; later.

(definterface IBody
  (^String name [])  ;; return type String, no arguments
  (^double mass [])  ;; return type double
  (^double x [])
  (clone [] "returns copy of self")   ; return type defaults to ^Object
  ;; 3 arguments of type double.  A deftype that implements this interface
  ;; must implement the method p!  The definterface must use:
  ;; _BANG_ for ! in Clojure method name
  ;; _PLUS_ for +
  ;; _ for -
  (p_BANG_ [^double x ^double y ^double z] "set pos.")
  ;; After name demangling, this must be implemented by Clojure method named v+!
  (v_PLUS__BANG_ [^double vx ^double vy ^double vz] "add to velocity"))
