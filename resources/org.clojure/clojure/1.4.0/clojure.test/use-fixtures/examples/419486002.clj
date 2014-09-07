; This fixture is intended to perform setup/teardown for each individual test in the namespace. Note that it assumes the :once fixture will handle creating/destroying the DB, while we only create/drop tables within the DB.
(defn another-fixture [f]
        (create-db-table)
        (f)
        (drop-db-table))

; Here we register another-fixture to wrap each test in the namespace
(use-fixtures :each another-fixture)