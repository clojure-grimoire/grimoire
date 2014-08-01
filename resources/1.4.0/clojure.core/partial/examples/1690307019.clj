user=> (def hundred-times (partial * 100))
#'user/hundred-times

user=> (hundred-times 5)
500

user=> (hundred-times 4 5 6)
12000

user=> (def add-hundred (partial + 100))
#'user/add-hundred

user=> (add-hundred 5)
105
