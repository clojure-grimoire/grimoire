sorted-map-by returns a sorted map that maintains its keys in sorted
order, as determined by the given comparator function.  See sorted-map
docs for the differences between sorted and unsorted maps.

Be cautious when writing your own comparators, especially for sorted
maps.  Remember that all maps follow the rule of 'first equal key to
be added wins'.  If your comparator function compares two values as
equal, then at most one of them can be a key in a sorted map at one
time.  See the 'Sorted sets and maps' section of (topic
Comparators) (TBD) for more discussion.

Examples:

    user=> (sorted-map-by > 2 "two" 3 "three" 11 "eleven" 5 "five" 7 "seven")
    {11 "eleven", 7 "seven", 5 "five", 3 "three", 2 "two"}
    user=> (sorted-map-by #(compare %2 %1)
                          "aardvark" "Orycteropus afer"
                          "lion" "Panthera leo"
                          "platypus" "Ornithorhynchus anatinus")
    {"platypus" "Ornithorhynchus anatinus",
     "lion" "Panthera leo",
     "aardvark" "Orycteropus afer"}

With comparator case-insensitive-cmp below, "Lion" is equal to "lion"
and not added as a separate key in the map.  The value associated with
the second equal key "Lion" does replace the first value.

    user=> (require '[clojure.string :as str])
    nil
    user=> (defn case-insensitive-cmp [s1 s2]
             (compare (str/lower-case s1) (str/lower-case s2)))
    #'user/case-insensitive-cmp
    user=> (sorted-map-by case-insensitive-cmp "lion" "normal lion"
                                               "Lion" "Orycteropus afer")
    {"lion" "Orycteropus afer"}

See also: sorted-map, (topic Comparators)
