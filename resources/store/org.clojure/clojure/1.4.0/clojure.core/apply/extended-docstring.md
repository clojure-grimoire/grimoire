f is a function and the last argument args is a sequence.  Calls f
with the elements of args as its arguments.  If more arguments are
specified (x y ...), they are added to the beginning of args to form
the complete argument list with which f is called.

Examples:

    user=> (apply + [1 2])           ; same as (+ 1 2)
    3
    user=> (apply + 1 2 [3 4 5 6])   ; same as (+ 1 2 3 4 5 6)
    21
    user=> (apply + [])              ; same as (+)
    0
    ;; This doesn't work because and is a macro, not a function
    user=> (apply and [true false true])
    CompilerException java.lang.RuntimeException: Can't take value of a macro: #'clojure.core/and, compiling:(NO_SOURCE_PATH:1:1)
