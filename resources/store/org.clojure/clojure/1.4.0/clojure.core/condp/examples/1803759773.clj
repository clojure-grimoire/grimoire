user=> (condp some [1 2 3 4]
         #{0 6 7} :>> inc
         #{5 9}   :>> dec)

java.lang.IllegalArgumentException: No matching clause: [1 2 3 4]