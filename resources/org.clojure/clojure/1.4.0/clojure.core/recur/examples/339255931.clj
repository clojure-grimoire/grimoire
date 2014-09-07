(loop [i 0]  
  (when (< i 5)    
    (println i)    
    (recur (inc i)); loop i will take this value
))