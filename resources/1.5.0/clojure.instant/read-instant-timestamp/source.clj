(def read-instant-timestamp
  "To read an instant as a java.sql.Timestamp, bind *data-readers* to a
map with this var as the value for the 'inst key. Timestamp preserves
fractional seconds with nanosecond precision. The timezone offset will
be used to convert into UTC."
  (partial parse-timestamp (validated construct-timestamp)))