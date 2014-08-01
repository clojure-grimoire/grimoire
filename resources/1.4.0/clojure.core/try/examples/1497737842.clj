;; for Clojurescript use js/Object as type
(try
   (/ 1 0)
   (catch js/Object e
       (.log js/console e)))