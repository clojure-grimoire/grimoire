(defn proxy-call-with-super [call this meth]
 (let [m (proxy-mappings this)]
    (update-proxy this (assoc m meth nil))
    (try
      (call)
      (finally (update-proxy this m)))))