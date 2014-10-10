user=> (def my-array (into-array Integer/TYPE [1 2 3]))
#'user/my-array

user=> (aset my-array 1 10) ; Set the element with index 1 to 10
10

user=> (into [] my-array)
[1 10 3]