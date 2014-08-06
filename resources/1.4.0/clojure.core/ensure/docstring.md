  Must be called in a transaction. Protects the ref from modification
  by other transactions.  Returns the in-transaction-value of
  ref. Allows for more concurrency than (ref-set ref @ref)