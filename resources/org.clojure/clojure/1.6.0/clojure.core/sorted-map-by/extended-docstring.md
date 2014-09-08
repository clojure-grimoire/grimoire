sorted-map-by returns a sorted map that maintains its keys in sorted
order, as determined by the given comparator function.  See sorted-map
docs for the differences between sorted and unsorted maps.

Be cautious when writing your own comparators, especially for sorted
maps.  Remember that all maps follow the rule of 'first equal key to
be added wins'.  If your comparator function compares two values as
equal, then at most one of them can be a key in a sorted map at one
time.
