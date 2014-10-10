user=> (refer 'clojure.string :only '[capitalize trim])
nil

user=> (capitalize (trim " hOnduRAS  "))
"Honduras"