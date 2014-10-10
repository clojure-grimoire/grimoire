user> (clojure.java.io/file "/tmp/foo")
#<File /tmp/foo>

user> (clojure.java.io/file "http://asdf.com")
#<File http:/asdf.com>

user> (clojure.java.io/file "/tmp/foo" "bar")
#<File /tmp/foo/bar>