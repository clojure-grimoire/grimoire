(defprotocol ClassResolver
  (^InputStream resolve-class [this name]
                "Given a class name, return that typeref's class bytes as an InputStream."))