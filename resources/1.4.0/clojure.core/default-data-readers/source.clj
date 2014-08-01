(def ^{:added "1.4"} default-data-readers
  "Default map of data reader functions provided by Clojure. May be
  overridden by binding *data-readers*."
  {'inst #'clojure.instant/read-instant-date
   'uuid #'clojure.uuid/default-uuid-reader})