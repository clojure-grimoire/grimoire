sorted-set-by returns a sorted set that maintains its elements in
sorted order, as determined by the given comparator function.  See
sorted-set docs for the differences between sorted and unsorted sets.

Be cautious when writing your own comparators, especially for sorted
sets.  Remember that all sets follow the rule of 'first equal element
to be added wins'.  If your comparator function compares two values as
equal, then at most one of them can be an element in a sorted set at
one time.  See the 'Sorted sets and maps' section of (topic
Comparators) (TBD) for more discussion.

Examples:

    user=> (sorted-set-by compare "Food" "good" "air" "My" "AiR" "My")
    #{"AiR" "Food" "My" "air" "good"}

With case-insensitive-cmp, "AiR" is a duplicate with "air" and
not added to the set, and the order is different.

    user=> (require '[clojure.string :as str])
    nil
    user=> (defn case-insensitive-cmp [s1 s2]
             (compare (str/lower-case s1) (str/lower-case s2)))
    #'user/case-insensitive-cmp
    user=> (sorted-set-by case-insensitive-cmp
                          "Food" "good" "air" "My" "AiR" "My")
    #{"air" "Food" "good" "My"}

See also: sorted-set, (topic Comparators)
