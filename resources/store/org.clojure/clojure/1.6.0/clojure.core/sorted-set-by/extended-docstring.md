sorted-set-by returns a sorted set that maintains its elements in
sorted order, as determined by the given comparator function.  See
sorted-set docs for the differences between sorted and unsorted sets.

Be cautious when writing your own comparators, especially for sorted
sets.  Remember that all sets follow the rule of 'first equal element
to be added wins'.  If your comparator function compares two values as
equal, then at most one of them can be an element in a sorted set at
one time.

