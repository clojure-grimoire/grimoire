;; an example for delay using an event-queue
user> (import  [java.util.concurrent PriorityBlockingQueue])
java.util.concurrent.PriorityBlockingQueue
user> (defn create-event-element [delayed-event tme]
  (struct event delayed-event tme))
#'user/create-event-element
user> (defn comp-queue [e1 e2]
  (if (< (:time e1) (:time e2))
    true false))
#'user/comp-queue
user> (defn update [n]
	(reset! c n))
#'user/update
user> (defn create-event-queue [comp-queue size]
  (new PriorityBlockingQueue size (comp comp-queue)))
#'user/create-event-queue
user> (def queue (create-event-queue comp-queue 10))
#'user/queue
user> (def elements (take 10 (repeatedly 
			      (fn[](create-event-element 
				    (delay (update (rand-int 20)))
				    (rand))))))
#'user/elements
user> (def c (atom 0))
#'user/c
user> @c
0
user> (doseq [e elements]
	     (.add queue e))
nil
user> (dotimes [_ 10]
	       (let [e (.poll queue)]
		    (println "c=" @c)
		    (print "time=" (:time e) ":")
		    (println (force (:object e)))))
c= 0
time= 0.07805244345581108 :19
c= 19
time= 0.24297414417455565 :6
c= 6
time= 0.24427040715816817 :0
c= 0
time= 0.24938478920862384 :17
c= 17
time= 0.33612588239752494 :6
c= 6
time= 0.5148481493716295 :5
c= 5
time= 0.5823642080700586 :7
c= 7
time= 0.7674970100941858 :4
c= 4
time= 0.9206272921555505 :14
c= 14
time= 0.9958255204018474 :4
nil
user> @c
4
user> (def elements (take 10 (repeatedly 
			      (fn[](create-event-element 
				    (delay (update (rand-int 20)))
				    (rand))))))
#'user/elements
;; if we check 'element', delay objects will be evaluated. The below is
;; this example. Please compare the above with the below.
user> elements  
({:object #<Delay@37a63e06: 16>, :time 0.48566816399656854} {:object #<Delay@3321875: 19>, :time 0.9374202154797486} {:object #<Delay@4de3aaf6: 17>, :time 0.3271116626875401} {:object #<Delay@1ded246d: 15>, :time 0.8843712542267577} {:object #<Delay@3bf27f74: 10>, :time 0.86383171974926} {:object #<Delay@2d8db76b: 14>, :time 0.2120086056700251} {:object #<Delay@3304e92a: 5>, :time 0.9406336968276247} {:object #<Delay@767de91: 0>, :time 0.2150071400135528} {:object #<Delay@34450563: 7>, :time 0.7520042839572664} {:object #<Delay@1f64c164: 1>, :time 0.6264819751284463})
;; The object of the last elements is #<Delay@1f64c164: 1>. Therefore,
;; This indicates the atom 'c' has already updated.
user> @c  
1 
user> (doseq [e elements]
	     (.add queue e))
nil
;; 'atom c' has never been updated because it has already
;; been evaluated.
user> (dotimes [_ 10]
	       (let [e (.poll queue)]
		    (println "c=" @c)
		    (print "time=" (:time e) ":")
		    (println (force (:object e)))))
c= 1
time= 0.2120086056700251 :14
c= 1
time= 0.2150071400135528 :0
c= 1
time= 0.3271116626875401 :17
c= 1
time= 0.48566816399656854 :16
c= 1
time= 0.6264819751284463 :1
c= 1
time= 0.7520042839572664 :7
c= 1
time= 0.86383171974926 :10
c= 1
time= 0.8843712542267577 :15
c= 1
time= 0.9374202154797486 :19
c= 1
time= 0.9406336968276247 :5
nil
user> 