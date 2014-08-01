(defn pprint-newline
  "Print a conditional newline to a pretty printing stream. kind specifies if the 
newline is :linear, :miser, :fill, or :mandatory. 

This function is intended for use when writing custom dispatch functions.

Output is sent to *out* which must be a pretty printing writer."
  {:added "1.2"}
  [kind] 
  (check-enumerated-arg kind #{:linear :miser :fill :mandatory})
  (nl *out* kind))