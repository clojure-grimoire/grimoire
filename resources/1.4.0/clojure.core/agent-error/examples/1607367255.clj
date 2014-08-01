(def tdate (agent (java.util.Date.)))

@tdate
=> #<Date Wed Feb 15 23:25:26 CET 2012>

(send tdate inc) ;;this has no meaning, rendering a (silent) error

(agent-error tdate)
=> #<ClassCastException java.lang.ClassCastException: java.util.Date cannot be cast to java.lang.Number>