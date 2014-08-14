user=> (def x (byte-array [(byte 0x43) 
                           (byte 0x6c)
                           (byte 0x6f)
                           (byte 0x6a)
                           (byte 0x75)
                           (byte 0x72)
                           (byte 0x65)
                           (byte 0x21)]))
#'user/x

user=> (String. x)
"Clojure!"
