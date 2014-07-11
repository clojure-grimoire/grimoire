## Arities
(.instanceMember instance args*)
(.instanceMember Classname args*)
(Classname/staticMethod args*)
Classname/staticField

## Documentation
{%raw%}
The instance member form works for both fields and methods.
  They all expand into calls to the dot operator at macroexpansion time.
{%endraw%}
