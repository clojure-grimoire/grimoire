(defn init-proxy
  "Takes a proxy instance and a map of strings (which must
  correspond to methods of the proxy superclass/superinterfaces) to
  fns (which must take arguments matching the corresponding method,
  plus an additional (explicit) first arg corresponding to this, and
  sets the proxy's fn map.  Returns the proxy."
  {:added "1.0"}
  [^IProxy proxy mappings]
    (. proxy (__initClojureFnMappings mappings))
    proxy)