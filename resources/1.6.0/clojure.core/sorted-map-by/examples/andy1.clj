user=> (sorted-map-by > 2 "two" 3 "three" 11 "eleven" 5 "five" 7 "seven")
{11 "eleven", 7 "seven", 5 "five", 3 "three", 2 "two"}
user=> (sorted-map-by #(compare %2 %1)
                      "aardvark" "Orycteropus afer"
                      "lion" "Panthera leo"
                      "platypus" "Ornithorhynchus anatinus")
{"platypus" "Ornithorhynchus anatinus",
 "lion" "Panthera leo",
 "aardvark" "Orycteropus afer"}
