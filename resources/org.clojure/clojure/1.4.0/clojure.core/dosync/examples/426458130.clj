;; Keep dosync body free of side-effects:
(defn my-thread-unsafe-fn [important-ref]
    (let [start-work (ref false)]
       (dosync
           (when (not @important-ref)
                ;"If a conflict occurs between 2 transactions 
                ;trying to modify the same reference, 
                ;one of them will be retried."
                ;http://clojure.org/concurrent_programming
                (ref-set important-ref true)
                (ref-set start-work true)))
        (when @start-work 
             ;launch side-effects here
            )))
