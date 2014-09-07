;This example converts what would be comma-separated values into pipe '|' ;separated values for alternate database loads. By switching delimiters, 
;quotes can be eliminated from each sequence element, which are not 
;needed for some databases.

(def test-data-in '(("9990999" "43" "ROADWAY" "MORRISON, VAN X DMD" "43 ROADWAY" "SOMETHINGTON" "XA" "00000" "501" "18050" "2500" "1180" "14370" "0") ("9990998" "25" "GARDEN PATH" "JANE SMITH N" "25  GARDEN PATH" "SOMETHINGTON" "ZA" "00000" "501" "1120" "600" "80" "440" "0"))

(def test-data-out (map #(concat (interpose \| %) (list \| "\n")) d2))

test-data-out

(("9990999" \| "43" \| "ROADWAY" \| "MORRISON, VAN X DMD" \| "43  ROADWAY" \| "SOMETHINGTON" \| "ZA" \| "00000" \| "501" \| "18050" \| "2500" \| "1180" \| "14370" \| "0" \| "\n") ("9990998" \| "25" \| "GARDEN PATH" \| "JANE SMITH N" \| "25  GARDEN PATH" \| "SOMETHINGTON" \| "ZA" \| "00000" \| "501" \| "1120" \| "600" \| "80" \| "440" \| "0" \| "\n"))

(doseq [in-seq d3] (doseq [val in-seq] (spit "temp1.csv" val :append true)))

cat temp1.csv

9990999|43|ROADWAY|MORRISON VAN X DMD|43 ROADWAY|SOMETHINGTON|ZA|00000|501|18050|2500|1180|14370|0|
9990998|25|GARDEN PATH|JANE SMITH N N|25  GARDEN PATH|SOMETHINGTON|A|00000|501|1120|600|80|440|0|
