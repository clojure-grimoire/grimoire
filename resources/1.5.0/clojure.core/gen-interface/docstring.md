  When compiling, generates compiled bytecode for an interface with
  the given package-qualified :name (which, as all names in these
  parameters, can be a string or symbol), and writes the .class file
  to the *compile-path* directory.  When not compiling, does nothing.
 
  In all subsequent sections taking types, the primitive types can be
  referred to by their Java names (int, float etc), and classes in the
  java.lang package can be used without a package qualifier. All other
  classes must be fully qualified.
 
  Options should be a set of key/value pairs, all except for :name are
  optional:

  :name aname

  The package-qualified name of the class to be generated

  :extends [interface ...]

  One or more interfaces, which will be extended by this interface.

  :methods [ [name [param-types] return-type], ...]

  This parameter is used to specify the signatures of the methods of
  the generated interface.  Do not repeat superinterface signatures
  here.