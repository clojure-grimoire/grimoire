;This example demonstrates how to add type information to regular clojure maps
(defn purchase-order [id date amount]
  ^{:type ::PurchaseOrder} ;metadata
   {:id id :date date :amount amount})

(def my-order (purchase-order 10 (java.util.Date.) 100.0))

(my-order)
=> {:id 10, :date #<Date Sun May 15 14:29:19 EDT 2011>, :amount 100.0}

(type my-order)
=> PurchaseOrder