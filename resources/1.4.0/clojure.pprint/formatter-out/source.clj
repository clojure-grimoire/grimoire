(defmacro formatter-out
  "Makes a function which can directly run format-in. The function is
fn [& args] ... and returns nil. This version of the formatter macro is
designed to be used with *out* set to an appropriate Writer. In particular,
this is meant to be used as part of a pretty printer dispatch method.

format-in can be either a control string or a previously compiled format."
  {:added "1.2"}
  [format-in]
  `(let [format-in# ~format-in
         cf# (if (string? format-in#) (#'clojure.pprint/cached-compile format-in#) format-in#)]
     (fn [& args#]
       (let [navigator# (#'clojure.pprint/init-navigator args#)]
         (#'clojure.pprint/execute-format cf# navigator#)))))