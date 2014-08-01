(defn reflect
  "Alpha - subject to change.
   Reflect on the type of obj (or obj itself if obj is a class).
   Return value and options are the same as for type-reflect. "
  {:added "1.3"}
  [obj & options]
  (apply type-reflect (if (class? obj) obj (class obj)) options))