(defn seque
  "Creates a queued seq on another (presumably lazy) seq s. The queued
  seq will produce a concrete seq in the background, and can get up to
  n items ahead of the consumer. n-or-q can be an integer n buffer
  size, or an instance of java.util.concurrent BlockingQueue. Note
  that reading from a seque can block if the reader gets ahead of the
  producer."
  {:added "1.0"
   :static true}
  ([s] (seque 100 s))
  ([n-or-q s]
   (let [^BlockingQueue q (if (instance? BlockingQueue n-or-q)
                             n-or-q
                             (LinkedBlockingQueue. (int n-or-q)))
         NIL (Object.) ;nil sentinel since LBQ doesn't support nils
         agt (agent (lazy-seq s)) ; never start with nil; that signifies we've already put eos
         log-error (fn [q e]
                     (if (.offer q q)
                       (throw e)
                       e))
         fill (fn [s]
                (when s
                  (if (instance? Exception s) ; we failed to .offer an error earlier
                    (log-error q s)
                    (try
                      (loop [[x & xs :as s] (seq s)]
                        (if s
                          (if (.offer q (if (nil? x) NIL x))
                            (recur xs)
                            s)
                          (when-not (.offer q q) ; q itself is eos sentinel
                            ()))) ; empty seq, not nil, so we know to put eos next time
                      (catch Exception e
                        (log-error q e))))))
         drain (fn drain []
                 (lazy-seq
                  (let [x (.take q)]
                    (if (identical? x q) ;q itself is eos sentinel
                      (do @agt nil)  ;touch agent just to propagate errors
                      (do
                        (send-off agt fill)
                        (cons (if (identical? x NIL) nil x) (drain)))))))]
     (send-off agt fill)
     (drain))))