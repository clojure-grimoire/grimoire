;; You can specify what encoding to use by giving a :encoding param, and an encoding string recognized by your JVM

user=> (slurp "/path/to/file" :encoding "ISO-8859-1")