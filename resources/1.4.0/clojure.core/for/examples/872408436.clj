;; Here are a couple of examples where the only difference is where
;; the :while is placed, but it makes a significant difference in the
;; behavior.

;; When x=2 y=1 is reached, :while (<= x y) evaluates false, so all
;; further items in the y collection are skipped.  When x=3 y=1 is
;; reached, the same thing happens.

user=> (for [x [1 2 3]
             y [1 2 3]
             :while (<= x y)
             z [1 2 3]]
         [x y z])
([1 1 1] [1 1 2] [1 1 3]
 [1 2 1] [1 2 2] [1 2 3]
 [1 3 1] [1 3 2] [1 3 3])

;; This is different.  When x=2 y=1 z=1 is reached, :while (<= x y)
;; evaluates false, but since the :while is after the binding for z,
;; all further items in the z collection are skipped.  Then x=2 y=2
;; z=1 is tried, where the while expresssion evaluates true.

user=> (for [x [1 2 3]
             y [1 2 3]
             z [1 2 3]
             :while (<= x y)]
         [x y z])
([1 1 1] [1 1 2] [1 1 3]
 [1 2 1] [1 2 2] [1 2 3]
 [1 3 1] [1 3 2] [1 3 3]
 [2 2 1] [2 2 2] [2 2 3]
 [2 3 1] [2 3 2] [2 3 3]
 [3 3 1] [3 3 2] [3 3 3])
