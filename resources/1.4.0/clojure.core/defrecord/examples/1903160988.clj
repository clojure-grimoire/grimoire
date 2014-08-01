;; prepare a protocol
user=> (defprotocol Fun-Time (drinky-drinky [_]))
Fun-Time

;; define a record and extend the previous protocol, implementing its function
user=> (defrecord Someone [nick-name preffered-drink] Fun-Time (drinky-drinky [_] (str nick-name "(having " preffered-drink "): uuumm")))
user.Someone
;; NOTE how 'nick-name' and 'preffered-drink' are symbols that are not declared anywhere, they are 'provided' inside the function

;; instantiate the protocol once and store it
user=> (def dude (->Someone "belun" "daiquiri"))
#'user/dude

;; use the function defined inside the protocol on the protocol instance
user=> (drinky-drinky dude)
"belun(having daiquiri): uuumm"


;; courtessy of Howard Lewis Ship - http://java.dzone.com/articles/changes-cascade-and-cautionary