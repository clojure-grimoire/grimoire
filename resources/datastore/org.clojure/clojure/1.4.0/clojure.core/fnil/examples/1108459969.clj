;; a function that expects a non-nil value
(defn say-hello [name] (str "Hello " name))
#'user/say-hello

;; fnil lets you create another function with a default
;; arg in case it is passed a nil
(def say-hello-with-defaults (fnil say-hello "World"))
#'user/say-hello-with-defaults

;; the happy path works as you would expect
(say-hello-with-defaults "Sir")
"Hello Sir"

;; but in the case that the function is passed a nil it will use the 
;; default supplied to fnil
(say-hello-with-defaults nil)
"Hello World"

;; this works with different arities too
(defn say-hello [first other] (str "Hello " first " and " other))
#'user/say-hello

;; lets create it with defaults
(def say-hello-with-defaults (fnil say-hello "World" "People"))
#'user/say-hello-with-defaults

;; call the function with all nil args - notice it uses the defaults
;; supplied to fnil
(say-hello-with-defaults nil nil)
"Hello World and People"

;; any of the args can be nil - the function will supply 
;; the default supplied with fnil
(say-hello-with-defaults "Sir" nil)
"Hello Sir and People"

;; and again - notice that "World" is the default here
(say-hello-with-defaults nil "Ma'am")
"Hello World and Ma'am"

;; or pass all args 
(say-hello-with-defaults "Sir" "Ma'am")
"Hello Sir and Ma'am"
