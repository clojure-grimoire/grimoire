user=> (derive ::Feline ::Animal)
nil
user=> (derive ::Cat ::Feline)
nil

user=> (derive ::Lion ::Feline)
nil

user=> (isa? ::Lion ::Feline)
true

user=> (isa? ::Lion ::Animal)
true

user=> (isa? ::Tuna ::Feline)
false