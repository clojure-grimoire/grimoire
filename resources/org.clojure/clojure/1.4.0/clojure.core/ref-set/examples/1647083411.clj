user=> (def foo (ref {}))
#'user/foo

user=> (dosync
         (ref-set foo {:foo "bar"}))
{:foo "bar"}

user=> @foo
{:foo "bar"}
