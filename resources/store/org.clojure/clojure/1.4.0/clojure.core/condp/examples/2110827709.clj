user=> (condp (comp seq re-seq) "foo=bar"
         #"[+](\w+)"    :>> #(vector (-> % first (nth 1) keyword) true)
         #"[-](\w+)"    :>> #(vector (-> % first (nth 1) keyword) false)
         #"(\w+)=(\S+)" :>> #(let [x (first %)]
                               [(keyword (nth x 1)) (nth x 2)]))

[:foo "bar"]