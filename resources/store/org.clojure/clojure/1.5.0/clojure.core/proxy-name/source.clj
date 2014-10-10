(defn proxy-name
 {:tag String} 
 [^Class super interfaces]
  (let [inames (into1 (sorted-set) (map #(.getName ^Class %) interfaces))]
    (apply str (.replace (str *ns*) \- \_) ".proxy"
      (interleave (repeat "$")
        (concat
          [(.getName super)]
          (map #(subs % (inc (.lastIndexOf ^String % "."))) inames)
          [(Integer/toHexString (hash inames))])))))