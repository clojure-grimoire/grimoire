(use 'clojure.zip) ;;warnings OK

(def zp (zipper vector? seq (fn [_ c] c) [[1 2 3] [4 [5 6] 7] [8 9]]) )

(root (insert-child (-> zp down right) 42))
=>([1 2 3] (42 4 [5 6] 7) [8 9])


(def zp2 (zipper vector? seq (fn [_ c] c) [74 75])

(root (insert-right (-> zp down right) zp2))
=>([1 2 3] [4 [5 6] 7] [[74 75] nil] [8 9])

(root (insert-left (-> zp down right) zp2))
=>([1 2 3] [[74 75] nil] [4 [5 6] 7] [8 9])



