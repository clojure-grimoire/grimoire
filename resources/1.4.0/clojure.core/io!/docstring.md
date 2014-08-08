  If an io! block occurs in a transaction, throws an
  IllegalStateException, else runs body in an implicit do. If the
  first expression in body is a literal string, will use that as the
  exception message.