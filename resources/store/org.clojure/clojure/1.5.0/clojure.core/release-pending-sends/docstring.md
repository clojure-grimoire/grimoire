  Normally, actions sent directly or indirectly during another action
  are held until the action completes (changes the agent's
  state). This function can be used to dispatch any pending sent
  actions immediately. This has no impact on actions sent during a
  transaction, which are still held until commit. If no action is
  occurring, does nothing. Returns the number of actions dispatched.