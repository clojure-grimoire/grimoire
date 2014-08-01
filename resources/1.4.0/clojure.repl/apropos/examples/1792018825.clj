user=> (apropos "temp")
()

user=> (require 'clojure.template)
nil

user=> (apropos "temp")
(apply-template do-template)
