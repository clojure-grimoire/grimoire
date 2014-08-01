(use 'clojure.zip)

;;using our old friend struct for performance in maps 
(struct element :httplink {:href "http://clojure.org"} nil)
=>
{:tag   :httplink, 
 :attrs {:href "http://clojure.org"}, 
 :content nil}

;;these elements can be output to ordinary xml strings with emit-element
(emit-element (struct element :httplink {:href "http://clojure.org"} nil))
=>
<httplink href='http://clojure.org'/>
;;this is printed - can be catched with macro with-out-str

;;make hierarchies:
(struct element :parent {:name "Barbara"} 
  [(struct element :children {:name "Gina"} nil) 
   (struct element :children {:name "John"} nil)])
=>
{:tag :parent, :attrs {:name "Barbara"}, :content 
   [{:tag :children, :attrs {:name "Gina"}, :content nil} 
    {:tag :children, :attrs {:name "John"}, :content nil}]}

;;which can be emit-elemented as well

(emit-element {:tag :parent, :attrs {:name "Barbara"}, :content [{:tag :children, :attrs {:name "Gina"}, :content nil} {:tag :children, :attrs {:name "John"}, :content nil}]})
=>
<parent name='Barbara'>
 <children name='Gina'/>
 <children name='John'/>
</parent>