;; the same example above in a simplified way
user=> (def wm (with-meta [1 2 3] {:my "meta"}))
#'user/wm

user=> wm
[1 2 3]

user=> (meta wm)
{:my "meta"}