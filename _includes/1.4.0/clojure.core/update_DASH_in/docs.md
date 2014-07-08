## Arities
[m [k & ks] f & args]

## Documentation
{%raw%}
'Updates' a value in a nested associative structure, where ks is a
  sequence of keys and f is a function that will take the old value
  and any supplied args and return the new value, and returns a new
  nested structure.  If any levels do not exist, hash-maps will be
  created.
{%endraw%}
