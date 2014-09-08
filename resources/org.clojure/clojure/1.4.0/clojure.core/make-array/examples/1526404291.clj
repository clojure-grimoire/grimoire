user=> (pprint (make-array Double/TYPE 3))
[0.0, 0.0, 0.0]

user=> (pprint (make-array Integer/TYPE 2 3))
[[0, 0, 0], [0, 0, 0]]


;; Create an array of Threads, then show content and type
user=> (def ar (make-array Thread 3))
#'user/ar

user=> (pprint ar)
[nil, nil, nil]

user=> (type ar)
[Ljava.lang.Thread;
