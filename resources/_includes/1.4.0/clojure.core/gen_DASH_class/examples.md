### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
(gen-class
	:name "some.package.RefMap"
	:implements [java.util.Map]
	:state "state"
	:init "init"
	:constructors {[] []}
	:prefix "ref-map-")

(defn ref-map-init []
	[[] (ref {})])

(defn ref-map-size [this]
	(let [state (.state this)] (.size @state)))

(defn ref-map-isEmpty [this]
	(let [state (.state this)] (.isEmpty @state)))

(defn ref-map-containsKey [this o]
	(let [state (.state this)] (.containsKey @state o)))

(defn ref-map-containsValue [this o]
	(let [state (.state this)] (.containsValue @state o)))

(defn ref-map-get [this o]
	(let [state (.state this)] (.get @state o)))

(defn ref-map-keySet [this]
	(let [state (.state this)] (.keySet @state)))

(defn ref-map-values [this]
	(let [state (.state this)] (.values @state)))

(defn ref-map-entrySet [this]
	(let [state (.state this)] (.entrySet @state)))

(defn ref-map-equals [this o]
	(let [state (.state this)] (.equals @state o)))

(defn ref-map-hashCode [this]
	(let [state (.state this)] (.hashCode @state)))

(defn ref-map-put [this k v]
	(let [state (.state this)]
		(dosync (alter state assoc k v)) v))

(defn ref-map-putAll [this m]
	(let [state (.state this)]
		(doseq [[k v] (map identity m)] (.put this k v))))

(defn ref-map-remove [this o]
	(let [state (.state this) v (get @state o)]
		(dosync (alter state dissoc o)) v))

(defn ref-map-clear [this]
	(let [state (.state this)]
		(dosync (ref-set state {}))))

(defn ref-map-toString [this]
	(let [state (.state this)] (.toString @state))){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; I found managing state a bit confusing at first.
;; here's a dumb little class with a getter and setter for a "location" field.

(ns com.example )

(gen-class
      :name com.example.Demo
      :state state
      :init init
      :prefix "-"
      :main false
      ;; declare only new methods, not superclass methods
      :methods [[setLocation [String] void]
                [getLocation [] String]])

;; when we are created we can set defaults if we want.
(defn -init []
  "store our fields as a hash"
  [[] (atom {:location "default"})])

;; little functions to safely set the fields.
(defn setfield
  [this key value]
      (swap! (.state this) into {key value}))

(defn getfield
  [this key]
  (@(.state this) key))

;; "this" is just a parameter, not a keyword
(defn -setLocation [this loc]
  (setfield this :location loc))

(defn  -getLocation
  [this]
  (getfield this :location))

;; running it -- you must compile and put output on the classpath
;; create a Demo, check the default value, then set it and check again.
user=> (def ex (com.example.Demo.))
#'user/ex
user=> (.getLocation ex)
"default"
user=> (.setLocation ex "time")
nil
user=> (.getLocation ex)
"time"
{% endraw %}
{% endhighlight %}


