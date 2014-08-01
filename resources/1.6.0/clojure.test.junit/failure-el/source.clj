(defn failure-el
  [message expected actual]
  (message-el 'failure message (pr-str expected) (pr-str actual)))