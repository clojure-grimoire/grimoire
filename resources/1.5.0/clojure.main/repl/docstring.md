Generic, reusable, read-eval-print loop. By default, reads from *in*,
  writes to *out*, and prints exception summaries to *err*. If you use the
  default :read hook, *in* must either be an instance of
  LineNumberingPushbackReader or duplicate its behavior of both supporting
  .unread and collapsing CR, LF, and CRLF into a single \newline. Options
  are sequential keyword-value pairs. Available options and their defaults:

     - :init, function of no arguments, initialization hook called with
       bindings for set!-able vars in place.
       default: #()

     - :need-prompt, function of no arguments, called before each
       read-eval-print except the first, the user will be prompted if it
       returns true.
       default: (if (instance? LineNumberingPushbackReader *in*)
                  #(.atLineStart *in*)
                  #(identity true))

     - :prompt, function of no arguments, prompts for more input.
       default: repl-prompt

     - :flush, function of no arguments, flushes output
       default: flush

     - :read, function of two arguments, reads from *in*:
         - returns its first argument to request a fresh prompt
           - depending on need-prompt, this may cause the repl to prompt
             before reading again
         - returns its second argument to request an exit from the repl
         - else returns the next object read from the input stream
       default: repl-read

     - :eval, funtion of one argument, returns the evaluation of its
       argument
       default: eval

     - :print, function of one argument, prints its argument to the output
       default: prn

     - :caught, function of one argument, a throwable, called when
       read, eval, or print throws an exception or error
       default: repl-caught