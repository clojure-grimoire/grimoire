;; data.xml from https://github.com/clojure/data.xml/
(use '[clojure.data.xml :only [parse-str]])

user=> (let [xml-text "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                      <foo key=\"val\">1<bar>2</bar>3</foo>"]
         (let [root (parse-str xml-text)]
           (xml-seq root)))

({:tag :foo,                                                   
  :attrs {:key "val"},                                         
  :content ("1" {:tag :bar, :attrs {}, :content ("2")} "3")}   
 "1"                                                           
 {:tag :bar, :attrs {}, :content ("2")}                        
 "2"                                                           
 "3")                                                          
