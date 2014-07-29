## Arities
[name & sigs]

## Documentation
{%raw%}
Creates a new Java interface with the given name and method sigs.
  The method return types and parameter types may be specified with type hints,
  defaulting to Object if omitted.

  (definterface MyInterface
    (^int method1 [x])
    (^Bar method2 [^Baz b ^Quux q]))
{%endraw%}
