;; print-dup is a multimethod, you can extend it to support new types.
;; The following statement adds print-dup support to 
;; the java.util.Date class
(defmethod print-dup java.util.Date [o w]
  (print-ctor o (fn [o w] (print-dup (.getTime  o) w)) w)) 