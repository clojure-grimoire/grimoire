user=> (ns-refers) ;;Oops! we missed a namespace (ns-refers 'user)
; Evaluation aborted.

user=> *e
#&lt;CompilerException java.lang.IllegalArgumentException: Wrong number of args passed to: core$ns-refers (NO_SOURCE_FILE:0)&gt;
