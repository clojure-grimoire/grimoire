(def xmlzipper (clojure.zip/xml-zip (clojure.xml/parse "resources/somedata.xml")))

;;make a zippper pointing at the children to the topnode in somedata.xml
(clojure.zip/children xmlzipper)

 




