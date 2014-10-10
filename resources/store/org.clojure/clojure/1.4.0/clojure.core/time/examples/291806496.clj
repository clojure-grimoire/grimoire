user=> (time (Thread/sleep 1000))
"Elapsed time: 1000.267483 msecs"
nil
user=> (with-out-str (time (Thread/sleep 1000)))
"\"Elapsed time: 1010.12942 msecs\"\n"

