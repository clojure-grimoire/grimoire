user=> (with-meta [1 2 3] {:my "meta"})
[1 2 3]

user=> (meta (with-meta [1 2 3] {:my "meta"}))
{:my "meta"}
