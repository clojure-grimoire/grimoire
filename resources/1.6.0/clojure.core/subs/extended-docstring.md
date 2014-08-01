The index of the first character is 0.  An exception will be
thrown if you use negative values -- it will not index characters from
the end of the string like similar functions in some other programming
languages.  If you use non-integer values for start or end, they will
be auto-converted to integers as if by (int x).

Examples:

    user=> (subs "abcdef" 1 3)
    "bc"
    user=> (subs "abcdef" 1)
    "bcdef"
    user=> (subs "abcdef" 4 6)
    "ef"
    user=> (subs "abcdef" 4 7)
    StringIndexOutOfBoundsException String index out of range: 7  java.lang.String.substring (String.java:1907)
    user=> (subs "abcdef" 5/3 6.28)   ; args converted to ints 1 6
    "bcdef"

Memory use warning:

subs, and many other functions that return substrings of a larger
one (e.g. re-find, re-seq, etc.) are based on Java's substring method
in class String.  Before Java version 7u6, this was implemented in
O(1) time by creating a String object that referred to an offset and
length within the original String object, thus retaining a reference
to the original as long as the substrings were referenced.  This can
cause unintentionally large memory use if you create large strings,
and then create small substrings of them with subs and similar
functions.  The large strings cannot be garbage collected because of
the references to them from the substrings.

In Java version 7u6, Java's substring() method behavior changed to
copy the desired substring into a new String object, so no references
are kept to the original.

    http://www.javaadvent.com/2012/12/changes-to-stringsubstring-in-java-7.html

If you wish to force the copying behavior, you can use the String
constructor (String. s).
