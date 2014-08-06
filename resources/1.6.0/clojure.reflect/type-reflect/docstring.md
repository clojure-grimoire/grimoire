  Alpha - subject to change.
   Reflect on a typeref, returning a map with :bases, :flags, and
  :members. In the discussion below, names are always Clojure symbols.

   :bases            a set of names of the type's bases
   :flags            a set of keywords naming the boolean attributes
                     of the type.
   :members          a set of the type's members. Each membrer is a map
                     and can be a constructor, method, or field.

   Keys common to all members:
   :name             name of the type 
   :declaring-class  name of the declarer
   :flags            keyword naming boolean attributes of the member

   Keys specific to constructors:
   :parameter-types  vector of parameter type names
   :exception-types  vector of exception type names

   Key specific to methods:
   :parameter-types  vector of parameter type names
   :exception-types  vector of exception type names
   :return-type      return type name

   Keys specific to fields:
   :type             type name

   Options:

     :ancestors     in addition to the keys described above, also
                    include an :ancestors key with the entire set of
                    ancestors, and add all ancestor members to
                    :members.
     :reflector     implementation to use. Defaults to JavaReflector,
                    AsmReflector is also an option.