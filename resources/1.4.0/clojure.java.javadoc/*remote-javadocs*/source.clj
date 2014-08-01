(def ^:dynamic *remote-javadocs*
 (ref (sorted-map
       "java." *core-java-api*
       "javax." *core-java-api*
       "org.ietf.jgss." *core-java-api*
       "org.omg." *core-java-api*
       "org.w3c.dom." *core-java-api*
       "org.xml.sax." *core-java-api*
       "org.apache.commons.codec." "http://commons.apache.org/codec/api-release/"
       "org.apache.commons.io." "http://commons.apache.org/io/api-release/"
       "org.apache.commons.lang." "http://commons.apache.org/lang/api-release/")))