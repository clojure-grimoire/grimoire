   (defn fjtask [^Callable f]
     (java.util.concurrent.ForkJoinTask/adapt f))