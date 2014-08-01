user=> (partition-by identity (sort "abcdaabccc"))
((\a \a \a) (\b \b) (\c \c \c \c) (\d))
