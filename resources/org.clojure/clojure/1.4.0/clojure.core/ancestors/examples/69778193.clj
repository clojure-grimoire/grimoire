;; use ancestors to show which classes ArrayList derives from and which
;; interfaces it implements

user=> (ancestors java.util.ArrayList)
#{java.util.Collection java.util.AbstractList java.io.Serializable java.lang.Cloneable java.util.List java.lang.Object java.util.AbstractCollection java.util.RandomAccess java.lang.Iterable}
user=>