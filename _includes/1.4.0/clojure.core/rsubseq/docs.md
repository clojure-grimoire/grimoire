## Arities
[sc test key]
[sc start-test start-key end-test end-key]

## Documentation
sc must be a sorted collection, test(s) one of <, <=, > or
  >=. Returns a reverse seq of those entries with keys ek for
  which (test (.. sc comparator (compare ek key)) 0) is true
