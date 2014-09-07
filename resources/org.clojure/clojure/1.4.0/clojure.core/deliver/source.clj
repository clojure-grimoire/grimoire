(defn deliver
  "Alpha - subject to change.
  Delivers the supplied value to the promise, releasing any pending
  derefs. A subsequent call to deliver on a promise will throw an exception."
  {:added "1.1"
   :static true}
  [promise val] (promise val))