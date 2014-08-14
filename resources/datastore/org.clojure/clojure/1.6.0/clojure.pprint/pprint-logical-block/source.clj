(defmacro pprint-logical-block 
  "Execute the body as a pretty printing logical block with output to *out* which 
must be a pretty printing writer. When used from pprint or cl-format, this can be 
assumed. 

This function is intended for use when writing custom dispatch functions.

Before the body, the caller can optionally specify options: :prefix, :per-line-prefix, 
and :suffix."
  {:added "1.2", :arglists '[[options* body]]}
  [& args]
  (let [[options body] (parse-lb-options #{:prefix :per-line-prefix :suffix} args)]
    `(do (if (#'clojure.pprint/level-exceeded) 
           (.write ^java.io.Writer *out* "#")
           (do 
             (push-thread-bindings {#'clojure.pprint/*current-level*
                                    (inc (var-get #'clojure.pprint/*current-level*))
                                    #'clojure.pprint/*current-length* 0})
             (try  
              (#'clojure.pprint/start-block *out*
                           ~(:prefix options) ~(:per-line-prefix options) ~(:suffix options))
              ~@body
              (#'clojure.pprint/end-block *out*)
              (finally 
               (pop-thread-bindings)))))
         nil)))