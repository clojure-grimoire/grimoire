  Execute the body as a pretty printing logical block with output to *out* which 
must be a pretty printing writer. When used from pprint or cl-format, this can be 
assumed. 

This function is intended for use when writing custom dispatch functions.

Before the body, the caller can optionally specify options: :prefix, :per-line-prefix, 
and :suffix.