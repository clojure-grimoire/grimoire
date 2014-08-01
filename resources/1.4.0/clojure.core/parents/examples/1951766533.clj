;; simple example showing single parented derivation
;; then adding another parent

user=> (derive ::toy_poodle ::poodle)
nil
user=> (parents ::toy_poodle)
#{:user/poodle}
user=> (derive ::toy_poodle ::toy_dogs)
nil
user=> (parents ::toy_poodle)
#{:user/poodle :user/toy_dogs}
user=>