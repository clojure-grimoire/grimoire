user=> (next '(:alpha :bravo :charlie))
(:bravo :charlie)

user=> (next (next '(:one :two :three)))
(:three)

user=> (next (next (next '(:one :two :three))))
nil