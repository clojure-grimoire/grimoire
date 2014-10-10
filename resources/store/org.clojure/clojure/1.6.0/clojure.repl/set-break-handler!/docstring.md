  Register INT signal handler.  After calling this, Ctrl-C will cause
  the given function f to be called with a single argument, the signal.
  Uses thread-stopper if no function given.