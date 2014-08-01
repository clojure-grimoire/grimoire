(defn key-pres?
    "This function accepts a value (cmp-val) and a vector of vectors
    (parsed output from clojure-csv) and returns the match value
    back if found and nil if not found. 

    Using reduce, the function searches every vector row to see 
    if cmp-val is at the col-idx location in the vector."

    [cmp-val cmp-idx csv-data]
    (reduce
        (fn [ret-rc csv-row]
            (if (= cmp-val (nth csv-row col-idx nil))
                    (conj ret-rc cmp-val)))
        [] 
        csv-data))