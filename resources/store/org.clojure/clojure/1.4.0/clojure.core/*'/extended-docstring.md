The `comment' form is rarely used due to the fact that the reader retains
the form, and it affects the evaluation of the program.
Contrast this with the `#_' reader macro, by which the
comment is completely removed before evaluation.

For example, let's say you want to comment out the call to "bar" in:

    (defn foo [] 1) (defn bar [] 2) (defn baz [] 3)
    (+ (foo) (bar) (baz)) ;= 6

The following will return a NullPointerException:

    (+ (foo) (comment (bar)) (baz))

However, the #_ reader macro works fine:

    (+ (foo) #_(bar) (baz)) ;= 4
