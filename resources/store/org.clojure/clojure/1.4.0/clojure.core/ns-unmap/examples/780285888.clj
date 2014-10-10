user=> (def foo 1)
#'user/foo

user=> foo
1

user=> (ns-unmap 'user 'foo) ; explicit
nil

user=> (ns-unmap *ns* 'foo) ; convenient
nil

user=> foo
"Unable to resolve symbol: foo in this context"
