=> (try
     (/ 1 0)
     (catch Exception e (str "caught exception: " (.getMessage e))))

"caught exception: Divide by zero"