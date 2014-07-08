## Arities
[sc test key]
[sc start-test start-key end-test end-key]

## Documentation
{%raw%}
sc must be a sorted collection, test(s) one of <, <=, > or
  >=. Returns a seq of those entries with keys ek for
  which (test (.. sc comparator (compare ek key)) 0) is true
{%endraw%}
