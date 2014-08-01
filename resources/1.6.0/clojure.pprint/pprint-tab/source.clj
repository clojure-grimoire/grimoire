(defn pprint-tab 
  "Tab at this point in the pretty printing stream. kind specifies whether the tab
is :line, :section, :line-relative, or :section-relative. 

Colnum and colinc specify the target column and the increment to move the target
forward if the output is already past the original target.

This function is intended for use when writing custom dispatch functions.

Output is sent to *out* which must be a pretty printing writer.

THIS FUNCTION IS NOT YET IMPLEMENTED."
  {:added "1.2"}
  [kind colnum colinc] 
  (check-enumerated-arg kind #{:line :section :line-relative :section-relative})
  (throw (UnsupportedOperationException. "pprint-tab is not yet implemented")))