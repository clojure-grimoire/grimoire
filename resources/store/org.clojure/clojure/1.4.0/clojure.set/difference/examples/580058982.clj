user=> (difference #{1 2 3})
#{1 2 3}
user=> (difference #{1 2} #{2 3})
#{1}
user=> (difference #{1 2 3} #{1} #{1 4} #{3})
#{2}