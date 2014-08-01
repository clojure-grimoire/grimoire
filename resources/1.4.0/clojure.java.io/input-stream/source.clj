(defn ^InputStream input-stream
  "Attempts to coerce its argument into an open java.io.InputStream.
   Default implementations always return a java.io.BufferedInputStream.

   Default implementations are defined for OutputStream, File, URI, URL,
   Socket, byte array, and String arguments.

   If the argument is a String, it tries to resolve it first as a URI, then
   as a local file name.  URIs with a 'file' protocol are converted to
   local file names.

   Should be used inside with-open to ensure the InputStream is properly
   closed."
  {:added "1.2"}
  [x & opts]
  (make-input-stream x (when opts (apply hash-map opts))))