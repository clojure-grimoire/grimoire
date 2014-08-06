  Alpha - subject to change
  
  (defrecord name [fields*]  options* specs*)
  
  Currently there are no options.

  Each spec consists of a protocol or interface name followed by zero
  or more method bodies:

  protocol-or-interface-or-Object
  (methodName [args*] body)*

  Dynamically generates compiled bytecode for class with the given
  name, in a package with the same name as the current namespace, the
  given fields, and, optionally, methods for protocols and/or
  interfaces.

  The class will have the (immutable) fields named by
  fields, which can have type hints. Protocols/interfaces and methods
  are optional. The only methods that can be supplied are those
  declared in the protocols/interfaces.  Note that method bodies are
  not closures, the local environment includes only the named fields,
  and those fields can be accessed directy.

  Method definitions take the form:

  (methodname [args*] body)

  The argument and return types can be hinted on the arg and
  methodname symbols. If not supplied, they will be inferred, so type
  hints should be reserved for disambiguation.

  Methods should be supplied for all methods of the desired
  protocol(s) and interface(s). You can also define overrides for
  methods of Object. Note that a parameter must be supplied to
  correspond to the target object ('this' in Java parlance). Thus
  methods for interfaces will take one more argument than do the
  interface declarations. Note also that recur calls to the method
  head should *not* pass the target object, it will be supplied
  automatically and can not be substituted.

  In the method bodies, the (unqualified) name can be used to name the
  class (for calls to new, instance? etc).

  The class will have implementations of several (clojure.lang)
  interfaces generated automatically: IObj (metadata support) and
  IPersistentMap, and all of their superinterfaces.

  In addition, defrecord will define type-and-value-based =,
  and will defined Java .hashCode and .equals consistent with the
  contract for java.util.Map.

  When AOT compiling, generates compiled bytecode for a class with the
  given name (a symbol), prepends the current ns as the package, and
  writes the .class file to the *compile-path* directory.

  Two constructors will be defined, one taking the designated fields
  followed by a metadata map (nil for none) and an extension field
  map (nil for none), and one taking only the fields (using nil for
  meta and extension fields). Note that the field names __meta
  and __extmap are currently reserved and should not be used when
  defining your own records.

  Given (defrecord TypeName ...), two factory functions will be
  defined: ->TypeName, taking positional parameters for the fields,
  and map->TypeName, taking a map of keywords to field values.