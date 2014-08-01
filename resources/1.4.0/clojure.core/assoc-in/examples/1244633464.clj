(def ppl (atom {"persons" {"joe" {:age 1}}}))
(swap! ppl assoc-in ["persons" "bob"] {:age 11})

@ppl
{"persons" {"joe" {:age 1}, "bob" {:age 11}}}