user=> (defstruct person :name :age :height)
#'user/person

user=> (struct person "george" 22 115)
{:name "george", :age 22, :height 115}