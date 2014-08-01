;;getting meta-data for a defined symbol (as opposed to what it's pointing to.

user=> meta
#<core$meta clojure.core$meta@2e257f1b>

user=> (var meta)
#'clojure.core/meta

user=> (meta (var meta))
{:ns #<Namespace clojure.core>, :name meta, :file "clojure/core.clj", :line 178, :arglists ([obj]), :doc "Returns the metadata of obj, returns nil if there is no metadata.", :added "1.0"}
