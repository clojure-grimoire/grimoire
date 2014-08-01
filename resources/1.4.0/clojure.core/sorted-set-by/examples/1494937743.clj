;; Be cautious about comparison functions that only compare part of
;; the objects:
user=> (defn second-< [x y]
         (< (second x) (second y)))
user=> (sorted-set-by second-< [:a 1] [:b 1] [:c 1])
#{[:a 1]}

;; Where did the other elements go?

;; Replacing < with <= might look like a fix, but doesn't work,
;; either:
user=> (defn second-<= [x y]
         (<= (second x) (second y)))
user=> (def s2 (sorted-set-by second-<= [:a 1] [:b 1] [:c 1]))
#'user/s2
user=> s2
#{[:c 1] [:b 1] [:a 1]}
;; So far, so good, but set membership tests can't find the elements.
user=> (contains? s2 [:b 1])
false
user=> (s2 [:c 1])
nil

;; Here is one way to write a good comparison function.  When the two
;; objects are equal in the parts we care about, use the tie-breaker
;; 'compare' on the whole values to give them a consistent order that
;; is only equal if the entire values are equal.
user=> (defn second-<-with-tie-break [x y]
         (let [c (compare (second x) (second y))]
           (if (not= c 0)
             c
             ;; Otherwise we don't care as long as ties are broken
             ;; consistently.
             (compare x y))))
user=> (def s3 (sorted-set-by second-<-with-tie-break [:a 1] [:b 1] [:c 1]))
#'user/s3
user=> s3
#{[:a 1] [:b 1] [:c 1]}
user=> (contains? s3 [:b 1])
true
user=> (s3 [:c 1])
[:c 1]
;; All good now!