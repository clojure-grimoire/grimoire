(def hello (to-array "Hello World!"))

(aget hello 1)
\e

(aset hello 1 \b) ;;Mutability! Watch out!
\b

(dotimes [n (alength hello)] (print (aget hello n)))
Hbllo World!