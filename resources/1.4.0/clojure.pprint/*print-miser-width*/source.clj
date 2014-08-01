(def ^:dynamic
 ^{:doc "The column at which to enter miser style. Depending on the dispatch table, 
miser style add newlines in more places to try to keep lines short allowing for further 
levels of nesting.",
   :added "1.2"}
 *print-miser-width* 40)