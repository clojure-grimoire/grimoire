;; You can create a namespace, not switch to it and still work in, by storing it

;; create the namespace
user=> (def for-later-use (create-ns 'my-namespace))
#'user/for-later-use

;; assign a value for a variable
user=> (intern for-later-use 'my-var "some value")
#'my-namespace/my-var
;; notice how the "for-later-use" symbol has been evaluated to the namespace it represents

;; check the new variable
user=> my-namespace/my-var
"some value"

;; you can also work on a namespace by using the its name
;; (but quoting it) instead of the return of "create-ns"
user=> (intern 'my-namespace 'my-var "some other value")
#'my-namespace/my-var

;; check the new assignment and see what's changed
user=> my-namespace/my-var
"some other value"
