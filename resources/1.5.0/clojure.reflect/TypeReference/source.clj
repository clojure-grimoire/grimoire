(defprotocol TypeReference
  "A TypeReference can be unambiguously converted to a type name on
   the host platform.

   All typerefs are normalized into symbols. If you need to
   normalize a typeref yourself, call typesym."
  (typename [o] "Returns Java name as returned by ASM getClassName, e.g. byte[], java.lang.String[]"))