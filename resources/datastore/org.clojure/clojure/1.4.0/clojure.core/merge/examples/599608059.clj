user=> (merge {:a 1} nil)
{:a 1}

user=> (merge nil {:a 1})
{:a 1}

user> (merge nil nil)
nil
