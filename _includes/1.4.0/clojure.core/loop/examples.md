### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;looping is recursive in Clojure, the loop construct is a hack so that something like tail-recursive-optimization works in clojure.
user=> (defn my-re-seq [re string]
         "Something like re-seq"
         (let [matcher (re-matcher re string)]

           (loop [match (re-find matcher) ;loop starts with 2 set arguments
                  result []]
             (if-not match
               result
               (recur (re-find matcher)    ;loop with 2 new arguments
                      (conj result match))))))

#'user/my-re-seq

user=> (my-re-seq #"\d" "0123456789")
["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"]

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Read decoded MP3 data in loop (requires mp3plugin.jar on class path)
;; http://java.sun.com/javase/technologies/desktop/media/jmf/mp3/download.html

(import '(javax.sound.sampled AudioSystem AudioFormat$Encoding))

(let [mp3-file (java.io.File. "tryout.mp3")
      audio-in (AudioSystem/getAudioInputStream mp3-file)
      audio-decoded-in (AudioSystem/getAudioInputStream AudioFormat$Encoding/PCM_SIGNED audio-in)
      buffer (make-array Byte/TYPE 1024)]
  (loop []
    (let [size (.read audio-decoded-in buffer)]
      (when (> size 0)
        ;do something with PCM data
	(recur)))))
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(loop [x 10]
  (when (> x 1)
    (println x)
    (recur (- x 2)))){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
(defn find-needle [needle haystack]
  ;loop binds initial values once,
  ;then binds values from each recursion call
  (loop [needle needle
         maybe-here haystack
         not-here '()]

    (let [needle? (first maybe-here)]

      ;test for return or recur
      (if (or (= (str needle?) (str needle))
              (empty? maybe-here))

        ;return results
        [needle? maybe-here not-here]

        ;recur calls loop with new values
        (recur needle
               (rest maybe-here)
               (concat not-here (list (first maybe-here))))))))

user=>(find-needle "|" "hay|stack")
[\| (\| \s \t \a \c \k) (\h \a \y)]{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
; makes a simple template function that can be used in mustache way: http://mustache.github.com/
(defn template [tpl env]
  (loop [tpl tpl
         env env]
    (cond (empty? env)
          tpl
          :else
          (let [[key value] (first env)]
            (recur (try (clojure.string/replace tpl
                                                (re-pattern (str "\\{\\{" (name key) "\\}\\}"))
                                                value)
                        (catch Exception e tpl))
                   (rest env)))))){% endraw %}
{% endhighlight %}


