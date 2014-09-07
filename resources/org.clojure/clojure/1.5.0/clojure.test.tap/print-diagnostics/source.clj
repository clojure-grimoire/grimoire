(defn print-diagnostics [data]
  (when (seq t/*testing-contexts*)
    (print-tap-diagnostic (t/testing-contexts-str)))
  (when (:message data)
    (print-tap-diagnostic (:message data)))
  (print-tap-diagnostic (str "expected:" (pr-str (:expected data))))
  (if (= :pass (:type data))
    (print-tap-diagnostic (str "  actual:" (pr-str (:actual data))))
    (do
      (print-tap-diagnostic
       (str "  actual:"
        (with-out-str
          (if (instance? Throwable (:actual data))
            (stack/print-cause-trace (:actual data) t/*stack-trace-depth*)
            (prn (:actual data)))))))))