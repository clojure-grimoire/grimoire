(def test-map {:account-no 12345678 :lname "Jones" :fnam "Fred"})
(assoc test-map :fnam "Sue")
{:account-no 12345678, :lname "Jones", :fnam "Sue"}