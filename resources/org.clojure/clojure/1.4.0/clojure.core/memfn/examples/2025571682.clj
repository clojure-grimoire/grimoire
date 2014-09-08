user=> (def *files* (file-seq (java.io.File. "/tmp/")))
#'user/*files*
user=> (count (filter (memfn isDirectory) *files*))
68
user=> (count (filter #(.isDirectory %) *files*))
68
