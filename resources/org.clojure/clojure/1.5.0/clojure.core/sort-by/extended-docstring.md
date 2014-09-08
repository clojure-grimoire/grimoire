See extended docs for sort, all of which applies to sort-by.

Examples:

    user=> (sort-by count ["lummox" "antidisestablishmentarianism" "a"])
    ("a" "lummox" "antidisestablishmentarianism")
    user=> (sort-by first > [[8.67 -5] [5 1] [-22/7 3.0] [5 0]])
    ([8.67 -5] [5 1] [5 0] [-22/7 3.0])

The example in sort extended docs demonstrating a Java array being
modified applies to sort-by, too, including using aclone to copy the
array before sorting to avoid that issue.

See also: sort, compare, (topic Comparators)
