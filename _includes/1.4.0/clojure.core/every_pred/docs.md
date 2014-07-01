## Arities
[p]
[p1 p2]
[p1 p2 p3]
[p1 p2 p3 & ps]

## Documentation
Takes a set of predicates and returns a function f that returns true if all of its
  composing predicates return a logical true value against all of its arguments, else it returns
  false. Note that f is short-circuiting in that it will stop execution on the first
  argument that triggers a logical false result against the original predicates.
