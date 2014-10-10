;; if you have a sequence, perhaps lazy, int-array will figure out the size
user=> (aget (int-array [1 2 3]) 0)
1
user=> (int-array [1 2 3])
#&lt;int[] [i@263c8db9]&gt;

;; if you need a certain size, with a constant initial value
user=> (aget (int-array 5 1) 4)
1
user=> (alength (int-array 5))
5

;; finally, you can specify a size + a sequence, which will initialize the array 
;; by taking size from the sequence
user=> (alength (int-array 5 (range 10)))
5
;; which is equivalent to
user=> (alength (int-array (take 5 (range 10)))
5
