;; Note that subs uses method java.lang.String/substring
;; http://docs.oracle.com/javase/6/docs/api/java/lang/String.html#substring%28int,%20int%29

;; See this link for more details:

;; http://www.javaadvent.com/2012/12/changes-to-stringsubstring-in-java-7.html

;; Briefly, before Java version 7u6, Java's substring method was
;; guaranteed to work in O(1) time, by creating a String that refers
;; to the original string's characters, rather than copying them.

;; After Java 7u6, substring was changed to copy the string's original
;; characters, thus taking time linear in the length of the substring,
;; but it never refers to the original string.

;; The potential disadvantage of the pre-version-7u6 behavior is that
;; if you read in one or more strings with a large total size, and then
;; use methods based on substring to keep only a small subset of that,
;; e.g., because you parsed and found a small collection of substrings of
;; interest for your computation, the large strings will still have
;; references to them, and thus cannot be garbage collected, even if you
;; have no other references to them.

;; You can use the Java constructor (String. s) to guarantee that you
;; copy a string s.  (String. (subs s 5 20)) will copy the substring once
;; pre-version-7u6, but it will copy the substring twice
;; post-version-7u6.

;; I believe java.util.regex.Matcher method group, and
;; java.util.regex.Pattern method split, also have the same
;; behavior as substring.

;; This affects the behavior of Clojure functions such as:

;; In clojure.core:
;; subs, re-find, re-matches, re-seq
;; In clojure.string:
;; replace replace-first split
