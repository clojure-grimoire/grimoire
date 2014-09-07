(defn update-proxy
  "Takes a proxy instance and a map of strings (which must
  correspond to methods of the proxy superclass/superinterfaces) to
  fns (which must take arguments matching the corresponding method,
  plus an additional (explicit) first arg corresponding to this, and
  updates (via assoc) the proxy's fn map. nil can be passed instead of
  a fn, in which case the corresponding method will revert to the
  default behavior. Note that this function can be used to update the
  behavior of an existing instance without changing its identity.
  Returns the proxy."
  {:added "1.0"}
  [^IProxy proxy mappings]
    (. proxy (__updateClojureFnMappings mappings))
    proxy)