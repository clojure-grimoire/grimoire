(def read-instant-calendar
  "To read an instant as a java.util.Calendar, bind *data-readers* to a map with
this var as the value for the 'inst key.  Calendar preserves the timezone
offset."
  (partial parse-timestamp (validated construct-calendar)))