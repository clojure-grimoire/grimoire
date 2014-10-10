;; Formatting integers, with options, in one of many bases.

;; First arg true sends formatted output to *out*
user=> (cl-format true "~5d\n" 3)
    3
nil

;; First arg nil or false causes formatted output to be returned as string
user=> (cl-format nil "~5d" 3)
"    3"

user=> (cl-format nil "Pad with leading zeros ~5,'0d" 3)
"Pad with leading zeros 00003"

user=> (cl-format nil "Pad with leading asterisks ~5,'*d" 3)
"Pad with leading asterisks ****3"

;; If there is a way to specify left-justifying a number in a single
;; format string, please add it here.  It can be done by using one
;; cl-format invocation to get a formatted number as a string, and
;; then use the ~<width>a specifier on the result.
user=> (cl-format nil "~15a" (cl-format nil "~:d" 1234567))
"1,234,567      "

user=> (cl-format nil "Always print the sign ~5@d" 3)
"Always print the sign    +3"

user=> (cl-format nil "Use comma group-separator every 3 digits ~12:d" 1234567)
"Use comma group-separator every 3 digits    1,234,567"

user=> (cl-format nil "decimal ~d  binary ~b  octal ~o  hex ~x" 63 63 63 63)
"decimal 63  binary 111111  octal 77  hex 3f"

user=> (cl-format nil "base 7  ~7r  with width and zero pad  ~7,15,'0r" 63 63)
"base 7  120  with width and zero pad  000000000000120"

;; No need for you to do any conversions to use cl-format with BigInt,
;; BigInteger, or BigDecimal.
user=> (cl-format nil "cl-format handles BigInts ~15d" 12345678901234567890)
"cl-format handles BigInts 12345678901234567890"

user=> (cl-format nil "Be aware of auto-conversion  ~8,'0d  ~8,'0d" 2.4 -5/4)
"Be aware of auto-conversion  000002.4  0000-5/4"

;; This might look like a bug, but it is actually behavior specified by the
;; Common Lisp HyperSpec mentioned in the docs above.  If you don't want that
;; behavior (format "%08d" -2) might suit your purposes better.
user=> (cl-format nil "~8,'0d" -2)
"000000-2"