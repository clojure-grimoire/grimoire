(defn write 
  "Write an object subject to the current bindings of the printer control variables.
Use the kw-args argument to override individual variables for this call (and any 
recursive calls). Returns the string result if :stream is nil or nil otherwise.

The following keyword arguments can be passed with values:
  Keyword              Meaning                              Default value
  :stream              Writer for output or nil             true (indicates *out*)
  :base                Base to use for writing rationals    Current value of *print-base*
  :circle*             If true, mark circular structures    Current value of *print-circle*
  :length              Maximum elements to show in sublists Current value of *print-length*
  :level               Maximum depth                        Current value of *print-level*
  :lines*              Maximum lines of output              Current value of *print-lines*
  :miser-width         Width to enter miser mode            Current value of *print-miser-width*
  :dispatch            The pretty print dispatch function   Current value of *print-pprint-dispatch*
  :pretty              If true, do pretty printing          Current value of *print-pretty*
  :radix               If true, prepend a radix specifier   Current value of *print-radix*
  :readably*           If true, print readably              Current value of *print-readably*
  :right-margin        The column for the right margin      Current value of *print-right-margin*
  :suppress-namespaces If true, no namespaces in symbols    Current value of *print-suppress-namespaces*

  * = not yet supported
"
  {:added "1.2"}
  [object & kw-args]
  (let [options (merge {:stream true} (apply hash-map kw-args))]
    (binding-map (table-ize write-option-table options) 
      (binding-map (if (or (not (= *print-base* 10)) *print-radix*) {#'pr pr-with-base} {}) 
        (let [optval (if (contains? options :stream) 
                       (:stream options)
                       true) 
              base-writer (condp = optval
                            nil (java.io.StringWriter.)
                            true *out*
                            optval)]
          (if *print-pretty*
            (with-pretty-writer base-writer
              (write-out object))
            (binding [*out* base-writer]
              (pr object)))
          (if (nil? optval) 
            (.toString ^java.io.StringWriter base-writer)))))))