(defn deliver
  "Delivers the supplied value to the promise, releasing any pending
  derefs. A subsequent call to deliver on a promise will have no effect."
  {:added "1.1"
   :static true}
  [promise val] (promise val))