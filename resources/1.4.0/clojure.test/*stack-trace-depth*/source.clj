(def ^:dynamic
 ^{:doc "The maximum depth of stack traces to print when an Exception
  is thrown during a test.  Defaults to nil, which means print the 
  complete stack trace."
   :added "1.1"}
 *stack-trace-depth* nil)