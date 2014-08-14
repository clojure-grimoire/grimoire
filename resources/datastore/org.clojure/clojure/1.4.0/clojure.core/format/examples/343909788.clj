user=> (format "%5d" 3)
"    3"

user=> (format "Pad with leading zeros %07d" 5432)
"Pad with leading zeros 0005432"

user=> (format "Left justified :%-7d:" 5432)
"Left justified :5432   :"

user=> (format "Locale-specific group separators %,12d" 1234567)
"Locale-specific group separators    1,234,567"

user=> (format "decimal %d  octal %o  hex %x  upper-case hex %X" 63 63 63 63)
"decimal 63  octal 77  hex 3f  upper-case hex 3F"

user=> (format "%2$d %1$s" "Positional arguments" 23)
"23 Positional arguments"

;;    ====== Clojure format/printf and large integers =====

;; This big number doesn't fit in a Long.  It is a
;; clojure.lang.BigInt, which format cannot handle directly.
user=> (format "%5d" 12345678901234567890)
IllegalFormatConversionException d != clojure.lang.BigInt  java.util.Formatter$FormatSpecifier.failConversion (Formatter.java:3999)

;; You can convert it to a java.math.BigInteger, which format does handle.
user=> (format "%5d" (biginteger 12345678901234567890))
"12345678901234567890"

;; If you do this very often, you might want to use something like
;; format-plus to avoid sprinkling your code with calls to biginteger.
(defn coerce-unformattable-types [args]
  (map (fn [x]
         (cond (instance? clojure.lang.BigInt x) (biginteger x)
               (instance? clojure.lang.Ratio x) (double x)
               :else x))
       args))

(defn format-plus [fmt & args]
  (apply format fmt (coerce-unformattable-types args)))

;; Now this works:
user=> (format-plus "%5d" 12345678901234567890)
"12345678901234567890"