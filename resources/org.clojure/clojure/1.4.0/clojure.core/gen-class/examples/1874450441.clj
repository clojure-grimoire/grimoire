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
	(let [state (.state this)] (.toString @state)))