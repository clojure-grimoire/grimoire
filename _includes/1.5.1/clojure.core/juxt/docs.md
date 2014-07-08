## Arities
[f]
[f g]
[f g h]
[f g h & fs]

## Documentation
{%raw%}
Takes a set of functions and returns a fn that is the juxtaposition
  of those fns.  The returned fn takes a variable number of args, and
  returns a vector containing the result of applying each fn to the
  args (left-to-right).
  ((juxt a b c) x) => [(a x) (b x) (c x)]
{%endraw%}
