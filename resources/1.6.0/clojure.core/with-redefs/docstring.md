  binding => var-symbol temp-value-expr

  Temporarily redefines Vars while executing the body.  The
  temp-value-exprs will be evaluated and each resulting value will
  replace in parallel the root value of its Var.  After the body is
  executed, the root values of all the Vars will be set back to their
  old values.  These temporary changes will be visible in all threads.
  Useful for mocking out functions during testing.