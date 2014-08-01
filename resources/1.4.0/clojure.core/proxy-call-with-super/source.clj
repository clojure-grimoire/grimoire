(defn proxy-call-with-super [call this meth]
 (let [m (proxy-mappings this)]
    (update-proxy this (assoc m meth nil))
    (let [ret (call)]
      (update-proxy this m)
      ret)))