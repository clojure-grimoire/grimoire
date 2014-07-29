### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
; See https://clojure.github.io/clojure/clojure.test-api.html for details

; my-test-fixture will be passed a fn that will call all your tests
; (e.g. test-using-db).  Here you perform any required setup
; (e.g. create-db), then call the passed function f, then perform
; any required teardown (e.g. destroy-db).
(defn my-test-fixture [f]
        (create-db)
        (f)
        (destroy-db))

; Here we register my-test-fixture to be called once, wrapping ALL tests
; in the namespace
(use-fixtures :once my-test-fixture)

; This is a regular test function, which is to be wrapped using my-test-fixture
(deftest test-using-db
  (is ...
)){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
; This fixture is intended to perform setup/teardown for each individual test in the namespace. Note that it assumes the :once fixture will handle creating/destroying the DB, while we only create/drop tables within the DB.
(defn another-fixture [f]
        (create-db-table)
        (f)
        (drop-db-table))

; Here we register another-fixture to wrap each test in the namespace
(use-fixtures :each another-fixture){% endraw %}
{% endhighlight %}


