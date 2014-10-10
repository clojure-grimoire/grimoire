;; can't interchange INTs with LONGs, only F(int, int) or F(long, long)
;; F is a function, not an
;; overflow very easily as shown below.

(unchecked-subtract Long/MIN_VALUE 5555555554)
user=> 9223372031299220254

(unchecked-subtract Long/MIN_VALUE 1)
user=> java.lang.IllegalArgumentException: No matching method found: unchecked_subtract (NO_SOURCE_FILE:0)

(unchecked-subtract Long/MIN_VALUE Long/MIN_VALUE)
user=> 0

(unchecked-subtract Integer/MIN_VALUE Long/MIN_VALUE)
user=> java.lang.IllegalArgumentException: No matching method found: unchecked_subtract (NO_SOURCE_FILE:0)

(unchecked-subtract Integer/MIN_VALUE Integer/MIN_VALUE)
user=> 0

(unchecked-subtract Integer/MIN_VALUE 0)
user=> -2147483648

(unchecked-subtract Integer/MIN_VALUE 1)
user=> 2147483647

(unchecked-subtract Integer/MIN_VALUE 54444444)
user=> 2093039204

(unchecked-subtract 123456 654321)
user=> -530865