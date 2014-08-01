(def content-handler
  (let [push-content (fn [e c]
                       (assoc e :content (conj (or (:content e) []) c)))
        push-chars (fn []
                     (when (and (= *state* :chars)
                                (some (complement #(Character/isWhitespace (char %))) (str *sb*)))
                       (set! *current* (push-content *current* (str *sb*)))))]
    (new clojure.lang.XMLHandler
         (proxy [ContentHandler] []
           (startElement [uri local-name q-name ^Attributes atts]
             (let [attrs (fn [ret i]
                           (if (neg? i)
                             ret
                             (recur (assoc ret
                                           (clojure.lang.Keyword/intern (symbol (.getQName atts i)))
                                           (.getValue atts (int i)))
                                    (dec i))))
                   e (struct element
                             (. clojure.lang.Keyword (intern (symbol q-name)))
                             (when (pos? (.getLength atts))
                               (attrs {} (dec (.getLength atts)))))]
               (push-chars)
               (set! *stack* (conj *stack* *current*))
               (set! *current* e)
               (set! *state* :element))
             nil)
           (endElement [uri local-name q-name]
             (push-chars)
             (set! *current* (push-content (peek *stack*) *current*))
             (set! *stack* (pop *stack*))
             (set! *state* :between)
             nil)
           (characters [^chars ch start length]
             (when-not (= *state* :chars)
               (set! *sb* (new StringBuilder)))
             (let [^StringBuilder sb *sb*]
               (.append sb ch (int start) (int length))
               (set! *state* :chars))
             nil)
           (setDocumentLocator [locator])
           (startDocument [])
           (endDocument [])
           (startPrefixMapping [prefix uri])
           (endPrefixMapping [prefix])
           (ignorableWhitespace [ch start length])
           (processingInstruction [target data])
           (skippedEntity [name])
           ))))