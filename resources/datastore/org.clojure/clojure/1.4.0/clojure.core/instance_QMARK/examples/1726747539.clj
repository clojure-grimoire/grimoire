user=> (def al (new java.util.ArrayList))
#'user/al
user=> (instance? java.util.Collection al)
true
user=> (instance? java.util.RandomAccess al)
true
user=> (instance? java.lang.String al)
false