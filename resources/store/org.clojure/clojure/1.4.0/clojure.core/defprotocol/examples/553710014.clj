;; defprotocol does NOT support interfaces with variable argument lists, 
;; like [this & args]
;; (this is not documented anywhere... )

;; The workaround is to define the interface with the variable arg list in a fn
;; separately outside of the protocol, which then calls the protocol interface
;; with a slightly different name and an array in place of the variable list,
;; like:

(defprotocol MyProtocol
  (-my-fn [this args]))

(defn my-fn [this & args] (-my-fn this args))