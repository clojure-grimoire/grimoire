(defmacro with-sh-dir
  "Sets the directory for use with sh, see sh for details."
  {:added "1.2"}
  [dir & forms]
  `(binding [*sh-dir* ~dir]
     ~@forms))