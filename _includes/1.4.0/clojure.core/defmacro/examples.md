### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
(defmacro with-tree [tree & body]
  "works on a JTree and restores its expanded paths after executing body"
  `(let [tree# ~tree
         root# (.getRoot (.getModel tree#))
         expanded# (if-let [x# (.getExpandedDescendants
                                tree# (TreePath. root#))]
                     (enumeration-seq x#)
                     ())
         selectionpaths# (. selectionmodel# getSelectionPaths)]
     ~@body
     (doseq [path# expanded#]
       (.expandPath tree# path#))))

;; usage:

(with-tree *one-jtree-instance*
   ;; some code here...
  ){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
(defmacro unless [pred a b]
  `(if (not ~pred) ~a ~b))

;; usage:

(unless false (println "Will print") (println "Will not print")){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(def dbg 1)

(defmacro chk-flagM
  "Throws an exception if flag does not resolve; else returns flag's value."
  [flag]
  (if (not (resolve flag))
    (throw (Exception. (str 'flag " is not a valid var.")))
    flag))

(defn write-csv-file
  "Writes a csv file using a key and an s-o-s"
  [out-sos out-file]

  (if (>= (chk-flagM dbg) 2)
    (println (first out-sos), "\n", out-file))

  (spit out-file "" :append false)
  (with-open [out-data (io/writer out-file)]
      (csv/write-csv out-data (map #(concat % [""]) out-sos))))

{% endraw %}
{% endhighlight %}


