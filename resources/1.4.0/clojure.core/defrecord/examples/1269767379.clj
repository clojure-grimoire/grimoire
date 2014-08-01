; If you define a defrecord in one namespace and want to use it
; from another, first require the namespace and then import
; the record as a regular class.
; The require+import order makes sense if you consider that first
; the namespace has to be compiled--which generates a class for
; the record--and then the generated class must be imported.
; (Thanks to raek in #clojure for the explanations!)

; Namespace 1 in "my/data.clj", where a defrecord is declared
(ns my.data)

(defrecord Employee [name surname])


; Namescape 2 in "my/queries.clj", where a defrecord is used
(ns my.queries
  (:require my.data)
  (:import [my.data Employee]))

(println
  "Employees named Albert:"
  (filter #(= "Albert" (.name %))
    [(Employee. "Albert" "Smith")
     (Employee. "John" "Maynard")
     (Employee. "Albert" "Cheng")]))
  