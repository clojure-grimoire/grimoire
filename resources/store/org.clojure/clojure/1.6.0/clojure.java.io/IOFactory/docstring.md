  Factory functions that create ready-to-use, buffered versions of
   the various Java I/O stream types, on top of anything that can
   be unequivocally converted to the requested kind of stream.

   Common options include
   
     :append    true to open stream in append mode
     :encoding  string name of encoding to use, e.g. "UTF-8".

   Callers should generally prefer the higher level API provided by
   reader, writer, input-stream, and output-stream.