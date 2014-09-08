;; If your input has line delimiters you can switch on multiline with (?m)

user=> (def testcase "Line 1\nLine 2\nTarget Line\nLine 4\nNot a target line")
user=>(re-find #"(?im)^target.*$" testcase)
"Target Line"