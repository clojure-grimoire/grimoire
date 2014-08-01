;; clojure.string/reverse correctly treats UTF-16 surrogate pairs
;; as a unit, and does not reverse the 2 Java chars of the pair.  Good!
user=> (def s "smily \ud83d\ude03.")
#'user/s
user=> (def x (str/reverse s))
#'user/x
user=> (map #(format "%04X" (int %)) s)
("0073" "006D" "0069" "006C" "0079" "0020" "D83D" "DE03" "002E")
user=> (map #(format "%04X" (int %)) x)
("002E" "D83D" "DE03" "0020" "0079" "006C" "0069" "006D" "0073")
