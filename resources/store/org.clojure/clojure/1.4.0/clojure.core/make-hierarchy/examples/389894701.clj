;; use make-hierarchy to build your own local hierarchy for derive, isa?, etc. 
;; instead of using the global hierarchy
;; note that the first ancestors call returns a nil since that type does not 
;; exist in the global hierarchy

user=> (def h (make-hierarchy))
#'user/h
user=> (def h (derive h ::rect ::shape))
#'user/h
user=> (def h (derive h ::square ::rect))
#'user/h
user=> h
{:parents {:user/square #{:user/rect}, :user/rect #{:user/shape}}, :ancestors {:
user/square #{:user/shape :user/rect}, :user/rect #{:user/shape}}, :descendants
{:user/rect #{:user/square}, :user/shape #{:user/square :user/rect}}}
user=> (ancestors ::square)
nil
user=> (ancestors h ::square)
#{:user/shape :user/rect}
user=>