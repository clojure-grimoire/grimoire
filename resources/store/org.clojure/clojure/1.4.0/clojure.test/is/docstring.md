  Generic assertion macro.  'form' is any predicate test.
  'msg' is an optional message to attach to the assertion.
  
  Example: (is (= 4 (+ 2 2)) "Two plus two should be 4")

  Special forms:

  (is (thrown? c body)) checks that an instance of c is thrown from
  body, fails if not; then returns the thing thrown.

  (is (thrown-with-msg? c re body)) checks that an instance of c is
  thrown AND that the message on the exception matches (with
  re-find) the regular expression re.