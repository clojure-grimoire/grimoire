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
