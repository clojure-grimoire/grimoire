;; Opens the file 'myfile.txt' and prints out the contents.  The 
;; 'with-open' ensures that the reader is closed at the end of the 
;; form.  
;; 
;; Please note that reading a file a character at a time is not 
;; very efficient.

user=> (with-open [r (java.io.FileReader. "myfile.txt")] 
         (loop [c (.read r)] 
           (if (not= c -1)
             (do 
               (print (char c)) 
               (recur (.read r))))))
