user=> (replace '{0 ZERO, 1 ONE, 2 TWO} '(This is the code â€” 0 1 2 0))
(This is the code â€” ZERO ONE TWO ZERO)

user=> (replace {2 :two, 4 :four} [4 2 3 4 5 6 2])
[:four :two 3 :four 5 6 :two]
