(defmacro formatter
  "Makes a function which can directly run format-in. The function is
fn [stream & args] ... and returns nil unless the stream is nil (meaning 
output to a string) in which case it returns the resulting string.

format-in can be either a control string or a previously compiled format."
  {:added "1.2"}
  [format-in]
  `(let [format-in# ~format-in
         my-c-c# (var-get (get (ns-interns (the-ns 'clojure.pprint))
                               '~'cached-compile))
         my-e-f# (var-get (get (ns-interns (the-ns 'clojure.pprint))
                               '~'execute-format))
         my-i-n# (var-get (get (ns-interns (the-ns 'clojure.pprint))
                               '~'init-navigator))
         cf# (if (string? format-in#) (my-c-c# format-in#) format-in#)]
     (fn [stream# & args#]
       (let [navigator# (my-i-n# args#)]
         (my-e-f# stream# cf# navigator#)))))