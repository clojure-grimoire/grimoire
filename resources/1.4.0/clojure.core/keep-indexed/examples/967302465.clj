user=> (keep-indexed #(if (pos? %2) %1) [-9 0 29 -7 45 3 -8])
(2 4 5)
;; f takes 2 args: 'index' and 'value' where index is 0-based
;; when f returns nil the index is not included in final result
user=> (keep-indexed (fn [idx v]
                       (if (pos? v) idx)) [-9 0 29 -7 45 3 -8])
(2 4 5)