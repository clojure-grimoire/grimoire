(:use 'clojure.test)

(testing "Arithmetic"
  (testing "with positive integers"
    (is (= 4 (+ 2 2)))
    (is (= 7 (+ 3 4))))
  (testing "with negative integers"
    (is (= -4 (+ -2 -2)))
    (is (= -1 (+ 3 -4)))))
=> true


---------------------------------------------------------------------------

(testing "Arithmetic"
  (testing "with positive integers"
    (is (= 4 (+ 2 2)))
    (is (= 7 (+ 3 4))))
  (testing "with negative integers"
    (is (= -5 (+ -2 -2)))                ;error here
    (is (= -1 (+ 3 -4)))))

=> FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:641)
Arithmetic with negative integers        ;bread crumb trail
expected: (= -5 (+ -2 -2))
  actual: (not (= -5 -4))
true