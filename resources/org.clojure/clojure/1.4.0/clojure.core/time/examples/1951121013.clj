;; Time how long it takes to write a string to a file 100 times
(defn time-test []
  (with-open [w (writer "test.txt" :append false)]
    (dotimes [_ 100]
      (.write w "I am being written to a file."))))


user=> (time (time-test))
"Elapsed time: 19.596371 msecs"