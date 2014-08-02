(defprotocol CollFold
  (coll-fold [coll n combinef reducef]))