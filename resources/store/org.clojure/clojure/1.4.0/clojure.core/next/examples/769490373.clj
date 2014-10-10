;; next is used in the recursive call.  (This is a naive implementation for illustration only.  Using `rest` is usually preferred over `next`.)

(defn my-map [func a-list]
  (when a-list
    (cons (func (first a-list))
          (my-map func (next a-list)))))