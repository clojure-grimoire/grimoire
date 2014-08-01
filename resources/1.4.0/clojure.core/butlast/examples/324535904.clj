;really slow reverse
;put the last item of the list at the start of a new list, and recur over all but the last item of the list.
;butlast acts similar to next in that it returns null for a 1-item list.

(defn my-reverse
  ([a-list]
     (cond (= a-list nil) nil
           :else (cons (last a-list)
                       (my-reverse (butlast a-list))))))