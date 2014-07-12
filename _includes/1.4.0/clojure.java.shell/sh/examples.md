### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (use '[clojure.java.shell :only [sh]])

;; Note: The actual output you see from a command like this will look messier.
;; The output below has had all newline characters replaced with line
;; breaks.  You would see a big long string with \n characters in the middle.
user=> (sh "ls" "-aul")

{:exit 0,
 :out "total 64
drwxr-xr-x  11 zkim  staff    374 Jul  5 13:21 .
drwxr-xr-x  25 zkim  staff    850 Jul  5 13:02 ..
drwxr-xr-x  12 zkim  staff    408 Jul  5 13:02 .git
-rw-r--r--   1 zkim  staff     13 Jul  5 13:02 .gitignore
-rw-r--r--   1 zkim  staff  12638 Jul  5 13:02 LICENSE.html
-rw-r--r--   1 zkim  staff   4092 Jul  5 13:02 README.md
drwxr-xr-x   2 zkim  staff     68 Jul  5 13:15 classes
drwxr-xr-x   5 zkim  staff    170 Jul  5 13:15 lib
-rw-r--r--@  1 zkim  staff   3396 Jul  5 13:03 pom.xml
-rw-r--r--@  1 zkim  staff    367 Jul  5 13:15 project.clj
drwxr-xr-x   4 zkim  staff    136 Jul  5 13:15 src
", :err ""}{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (use '[clojure.java.shell :only [sh]])

user=> (println (:out (sh "cowsay" "Printing a command-line output")))

 _________________________________
< Printing a command-line output. >
 ---------------------------------
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||

nil{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (use '[clojure.java.shell :only [sh]])
nil

;; note that the options, like :in, have to go at the end of arglist
;; advantage of piping-in thru stdin is less need for quoting/escaping
user=> (println (:out (sh "cat" "-" :in "Printing input from stdin with funny chars like ' \" $@ & ")))
Printing input from stdin with funny chars like ' " $@ &
nil{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; sh is implemented using Clojure futures.  See examples for 'future'
;; for discussion of an undesirable 1-minute wait that can occur before
;; your standalone Clojure program exits if you do not use shutdown-agents.{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
(sh "pwd" :dir "/home/ics/icsdev")
{:exit 0, :out "/home/ics/icsdev\n", :err ""}{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
(require '[clojure.java.shell :as shell])
(shell/sh "sh" "-c" "cd /etc; pwd")
{:exit 0, :out "/etc\n", :err ""}{% endraw %}
{% endhighlight %}


