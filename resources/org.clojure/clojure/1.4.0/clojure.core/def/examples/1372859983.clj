;; give function another name
user=> (def sys-map map)
;; give macro another name
user=> (def #^{:macro true} sys-loop #'loop)