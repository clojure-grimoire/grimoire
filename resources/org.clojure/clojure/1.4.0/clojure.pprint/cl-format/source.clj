(defn cl-format 
  "An implementation of a Common Lisp compatible format function. cl-format formats its
arguments to an output stream or string based on the format control string given. It 
supports sophisticated formatting of structured data.

Writer is an instance of java.io.Writer, true to output to *out* or nil to output 
to a string, format-in is the format control string and the remaining arguments 
are the data to be formatted.

The format control string is a string to be output with embedded 'format directives' 
describing how to format the various arguments passed in.

If writer is nil, cl-format returns the formatted result string. Otherwise, cl-format 
returns nil.

For example:
 (let [results [46 38 22]]
        (cl-format true \"There ~[are~;is~:;are~]~:* ~d result~:p: ~{~d~^, ~}~%\" 
                   (count results) results))

Prints to *out*:
 There are 3 results: 46, 38, 22

Detailed documentation on format control strings is available in the \"Common Lisp the 
Language, 2nd edition\", Chapter 22 (available online at:
http://www.cs.cmu.edu/afs/cs.cmu.edu/project/ai-repository/ai/html/cltl/clm/node200.html#SECTION002633000000000000000) 
and in the Common Lisp HyperSpec at 
http://www.lispworks.com/documentation/HyperSpec/Body/22_c.htm
"
  {:added "1.2",
   :see-also [["http://www.cs.cmu.edu/afs/cs.cmu.edu/project/ai-repository/ai/html/cltl/clm/node200.html#SECTION002633000000000000000" 
               "Common Lisp the Language"]
              ["http://www.lispworks.com/documentation/HyperSpec/Body/22_c.htm"
               "Common Lisp HyperSpec"]]}
  [writer format-in & args]
  (let [compiled-format (if (string? format-in) (compile-format format-in) format-in)
        navigator (init-navigator args)]
    (execute-format writer compiled-format navigator)))