Caution: Partitioning lazy sequence code freeze

(def l [1 2 3 4 5])
;create a simple lazy sequence function testing only
;(rdr l) returns a lazy sequence from l
(def rdr (fn reader[x] (cons (first x) (lazy-seq (reader  (rest x))))))

;the line below will freeze
(doall (partition-all 2 (rdr l)) )

;add-in a take-while statement do exit the lazy sequence on nil
(doall (partition-all 2 (take-while (complement nil?) (rdr l))))