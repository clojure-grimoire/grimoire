Examples:

    user=> (range 11)
    (0 1 2 3 4 5 6 7 8 9 10)
    user=> (range 5 11)
    (5 6 7 8 9 10)
    user=> (range 5 11 2)
    (5 7 9)

    ;; Just as when increasing, when decreasing the final value is not
    ;; included in the result.
    user=> (range 11 0 -1)
    (11 10 9 8 7 6 5 4 3 2 1)
    user=> (range 11 -1 -1)
    (11 10 9 8 7 6 5 4 3 2 1 0)

Be cautious when using float or double values, due to round-off
errors.  This is especially true for range, because these round-off
errors can accumulate and increase over a large number of values.

    user=> (count (range 0 100 1))
    100
    user=> (last (range 0 100 1))
    99
    user=> (count (range 0.0 10.0 0.1))
    101
    user=> (last (range 0.0 10.0 0.1))
    9.99999999999998

Functions like double-range and rangef in namespace thalia.utils may
be closer to what you want in some cases.
