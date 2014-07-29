### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
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
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
(defn write-csv-file
  "Writes a csv file using a key and an s-o-s (sequence of sequences)"
  [out-sos out-file]

  (spit out-file "" :append false)
  (with-open [out-data (io/writer out-file)]
      (csv/write-csv out-data out-sos)))

{% endraw %}
{% endhighlight %}


