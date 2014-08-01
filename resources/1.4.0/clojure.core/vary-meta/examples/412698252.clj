;; continuing from the previous with-meta example
user=> (def wm (with-meta [1 2 3] {:my "meta"}))
#'user/wm

user=> wm
[1 2 3]

user=> (meta wm)
{:my "meta"}

user=> (def new-wm (vary-meta wm assoc :your "new meta"))
#'user/new-wm

user=> new-wm
[1 2 3]

user=> (meta new-wm)
{:my "meta", :your "new meta"}

