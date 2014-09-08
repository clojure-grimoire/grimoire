;;; with distructuring binding

;; successful case
(if-let [[w n] (re-find #"a(\d+)x" "aaa123xxx")]
  [w n]
  :not-found)  ;=> ["a123x" "123"]

;; unsuccessful case
(if-let [[w n] (re-find #"a(\d+)x" "bbb123yyy")]
  [w n]
  :not-found) ;=> :not-found

;; same as above
(if-let [[w n] nil]
  [w n]
  :not-found) ;=> :not-found

;; on Map
(if-let [{:keys [a b]} nil]
  [a b]
  :not-found) ;=> :not-found
