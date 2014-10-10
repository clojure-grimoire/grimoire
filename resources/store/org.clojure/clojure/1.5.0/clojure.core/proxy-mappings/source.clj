(defn proxy-mappings
  "Takes a proxy instance and returns the proxy's fn map."
  {:added "1.0"}
  [^IProxy proxy]
    (. proxy (__getClojureFnMappings)))