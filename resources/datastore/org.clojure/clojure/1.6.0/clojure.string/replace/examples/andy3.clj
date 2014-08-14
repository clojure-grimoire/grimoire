user=> (= (with-meta #{1 2 3} {:key1 1}) (with-meta #{1 2 3} {:key1 2}))
true                  ; Metadata is ignored when comparing
