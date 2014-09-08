(use 'clojure.xml)

;;If just a string it's text inside or something. just print it
(emit-element "hello")
hello

;;we want a map with a tag
(emit-element {:tag :hello})
<hello/>

;;strings works as well (since we're using the "name" function)
(emit-element {:tag "hello"})
<hello/>

;;:attrs is for all the attributes
(emit-element {:tag :hello :attrs {:place "world"}})
<hello place='world'/>

;;:content is for all the children in the element
(emit-element {:tag :parent :attrs {:id "22" :name "fritz"} :content [
                {:tag :child :attrs {:id "56"}} 
                {:tag :child :attrs {:id "57"}]}))
<parent id='22' name='fritz'>
<child id='56'/>
<child id='57'/>
</parent>

;;also, look out for not supplying :content with another xml-ish map:
(emit-element {:tag :hello :content "world"})
<hello>
java.lang.NullPointerException (NO_SOURCE_FILE:0)

;;it expects a {:tag :something}
