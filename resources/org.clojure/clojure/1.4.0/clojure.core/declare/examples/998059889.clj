user=> (defn foo []
         (undefined-func))
; Evaluation aborted. Unable to resolve symbol: undefined-func in this context
nil

user=> (declare undefined-func)
#'user/undefined-func

user=> (defn foo []
         (undefined-func))
#'user/foo
