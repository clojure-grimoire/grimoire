;; You are having a problem loading a redefined namespace:
user=> (load "src/clj/com/tizra/layout_expander.clj")
#<IllegalStateException java.lang.IllegalStateException: Alias xml already exists in namespace com.tizra.layout-expander, aliasing com.tizra.xml-match>

;; ns-unalias to the rescue!
user=> (ns-unalias (find-ns 'com.tizra.layout-expander) 'xml)
nil

user=> (load "src/clj/com/tizra/layout_expander.clj")
#'com.tizra.layout-expander/junk
