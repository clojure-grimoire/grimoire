;; for fast interop
user=> (bytes (byte-array (map (comp byte int) "ascii")))
#<byte[] [B@7a004f62>
user=> (def the-bytes *1)
#'user/the-bytes
user=> (defn get-byte [the-bytes i] (aget the-bytes i))
#'user/get-byte Reflection warning, NO_SOURCE_PATH:1 - call to aget can't be resolved.

user=> (defn get-byte [the-bytes i] 
         (let [the-bytes (bytes the-bytes)] 
           (aget the-bytes i)))
#'user/get-byte
user=> (get-byte the-bytes 0)
97
