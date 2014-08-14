(defn counter []  
  (let [tick (atom 0)]
    #(swap! tick inc)))

(def tick (counter))

user=> (take 10 (repeatedly tick))
(1 2 3 4 5 6 7 8 9 10)