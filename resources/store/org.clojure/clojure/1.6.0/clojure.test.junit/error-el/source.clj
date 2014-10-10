(defn error-el
  [message expected actual]
  (message-el 'error
              message
              (pr-str expected)
              (if (instance? Throwable actual)
                (with-out-str (stack/print-cause-trace actual t/*stack-trace-depth*))
                (prn actual))))