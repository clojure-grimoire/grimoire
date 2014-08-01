(defprotocol ^{:added "1.2"} IOFactory
  "Factory functions that create ready-to-use, buffered versions of
   the various Java I/O stream types, on top of anything that can
   be unequivocally converted to the requested kind of stream.

   Common options include
   
     :append    true to open stream in append mode
     :encoding  string name of encoding to use, e.g. \"UTF-8\".

   Callers should generally prefer the higher level API provided by
   reader, writer, input-stream, and output-stream."
  (^{:added "1.2"} make-reader [x opts] "Creates a BufferedReader. See also IOFactory docs.")
  (^{:added "1.2"} make-writer [x opts] "Creates a BufferedWriter. See also IOFactory docs.")
  (^{:added "1.2"} make-input-stream [x opts] "Creates a BufferedInputStream. See also IOFactory docs.")
  (^{:added "1.2"} make-output-stream [x opts] "Creates a BufferedOutputStream. See also IOFactory docs."))