user=> (defprotocol Area (get-area [this]))
Area

user=> (defrecord Rectangle [width height]
                  Area
                  (get-area [this]
                    (* width height)))
user.Rectangle

(extends? Area Rectangle)
true
