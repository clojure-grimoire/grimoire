(def an-array (int-array 25000 (int 0)))

user=> (time (amap ^ints an-array 
                   idx 
                   ret 
                   (+ (int 1) 
                      (aget ^ints an-array idx))))

"Elapsed time: 14.708653 msecs"

;; Note: without type hinting the performance of would not be good.