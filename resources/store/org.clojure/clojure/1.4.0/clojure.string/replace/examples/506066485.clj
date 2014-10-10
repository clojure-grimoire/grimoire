user=> (clojure.string/replace "The color is red." #"[aeiou]"  #(str %1 %1))
"Thee cooloor iis reed."
