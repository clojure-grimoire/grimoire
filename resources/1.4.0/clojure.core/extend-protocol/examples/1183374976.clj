(defprotocol XmlNode
  (as-xml [this]))

(defrecord User [^Integer id ^String name ^java.util.Date dob])

; protocols can be extended to existing types and user defined types
(extend-protocol XmlNode
  Integer
  (as-xml [this] (str this))
  String
  (as-xml [this] (identity this))
  java.util.Date
  (as-xml [this] (-> (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") .format this))
  User
  (as-xml [this] (str "<user>"
                      "<id>" (as-xml (:id this)) "</id>"
                      "<name>" (as-xml (:name this)) "</name>"
                      "<dob>" (as-xml (:dob this)) "</dob>")))