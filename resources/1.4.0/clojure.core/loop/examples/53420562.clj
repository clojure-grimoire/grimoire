(defn find-needle [needle haystack]
  ;loop binds initial values once,
  ;then binds values from each recursion call
  (loop [needle needle
         maybe-here haystack
         not-here '()]

    (let [needle? (first maybe-here)]

      ;test for return or recur
      (if (or (= (str needle?) (str needle))
              (empty? maybe-here))

        ;return results
        [needle? maybe-here not-here]

        ;recur calls loop with new values
        (recur needle
               (rest maybe-here)
               (concat not-here (list (first maybe-here))))))))

user=>(find-needle "|" "hay|stack")
[\| (\| \s \t \a \c \k) (\h \a \y)]