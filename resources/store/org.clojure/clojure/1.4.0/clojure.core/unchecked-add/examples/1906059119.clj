;; can't interchange INTs with LONGs, only F(int, int) or F(long, long)
;; F is a function, not an operator.
;; overflow very easily as shown below.

user=> (unchecked-add Integer/MAX_VALUE 0)
2147483647

user=> (unchecked-add Integer/MAX_VALUE 1)
-2147483648

user=> (unchecked-add Integer/MAX_VALUE Integer/MAX_VALUE)
-2

user=> (unchecked-add Integer/MAX_VALUE Long/MAX_VALUE)
java.lang.IllegalArgumentException: No matching method found: unchecked_add (NO_SOURCE_FILE:0)

user=> (unchecked-add Integer/MAX_VALUE Long/MAX_VALUE)
java.lang.IllegalArgumentException: No matching method found: unchecked_add (NO_SOURCE_FILE:0)

user=> (unchecked-add Long/MAX_VALUE Long/MAX_VALUE)
-2

user=> (unchecked-add 5 Long/MAX_VALUE)
java.lang.IllegalArgumentException: No matching method found: unchecked_add (NO_SOURCE_FILE:0)

user=> (unchecked-add 5555555555 Long/MAX_VALUE)
-9223372031299220254