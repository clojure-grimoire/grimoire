;;  ==== Clojure format/printf and floating-point formats ====
user=> (format "%.3f" 2.0)
"2.000"

;; format doesn't handle integers or ratios with %e, %f, %g, or %a
user=> (format "%.3f" 2)
IllegalFormatConversionException f != java.lang.Long  java.util.Formatter$FormatSpecifier.failConversion (Formatter.java:3999)

;; In general, if you want to use floating-point formats %e, %f, %g,
;; or %a with format or printf, and you don't know whether the values
;; you want to format are floats or doubles, you should convert them:
user=> (format "%.3f" (double 2))
"2.000"

user=> (format "%.3f" (double (/ 5 2)))
"2.500"

;; One could make a function that parses the format string to look for
;; %f and other floating-point formats and automatically coerces the
;; corresponding arguments to doubles, but such a function probably
;; wouldn't fit into a short example.  You could also consider using
;; cl-format which does handle these kinds of things for you.  The main
;; disadvantage to doing so is that you have to learn a different syntax
;; for format specifiers.