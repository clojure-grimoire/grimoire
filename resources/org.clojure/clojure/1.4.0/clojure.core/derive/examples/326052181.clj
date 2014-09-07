user=> (derive ::Cat ::Feline)
nil

user=> (derive ::Lion ::Feline)
nil

user=> (isa? ::Lion ::Feline)
true

user=> (isa? ::Tuna ::Feline)
false