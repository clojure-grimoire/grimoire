(def default-streams-impl
  {:make-reader (fn [x opts] (make-reader (make-input-stream x opts) opts))
   :make-writer (fn [x opts] (make-writer (make-output-stream x opts) opts))
   :make-input-stream (fn [x opts]
                        (throw (IllegalArgumentException.
                                (str "Cannot open <" (pr-str x) "> as an InputStream."))))
   :make-output-stream (fn [x opts]
                         (throw (IllegalArgumentException.
                                 (str "Cannot open <" (pr-str x) "> as an OutputStream."))))})