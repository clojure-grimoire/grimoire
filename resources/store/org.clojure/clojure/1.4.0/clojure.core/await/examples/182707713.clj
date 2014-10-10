(def *agnt* (agent {}))

user=> (send-off *agnt* (fn [state] 
                          (Thread/sleep 10000)
                          (assoc state :done true)))
#&lt;Agent@5db18235: {}&gt;

user=> (await *agnt*) ; blocks till the agent action is finished
nil