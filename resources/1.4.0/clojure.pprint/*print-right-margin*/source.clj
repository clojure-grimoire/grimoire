(def ^:dynamic
 ^{:doc "Pretty printing will try to avoid anything going beyond this column.
Set it to nil to have pprint let the line be arbitrarily long. This will ignore all 
non-mandatory newlines.",
   :added "1.2"}
 *print-right-margin* 72)