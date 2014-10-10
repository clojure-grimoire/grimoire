(defn reduce-csv-row
    "Accepts a csv-row (a vector) a list of columns to extract, 
     and reduces (and returns) a csv-row to a subset based on 
     selection using the values in col-nums (a vector of integer 
     vector positions.)"

    [csv-row col-nums]

    (reduce
        (fn [out-csv-row col-num]
            ; Don't consider short vectors containing junk.
            (if-not (<= (count csv-row) 1)
                (conj out-csv-row (nth csv-row col-num nil))))
        []
        col-nums))

