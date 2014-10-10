;; make a struct 'goods'. every goods has
;; an id and a price.
(defstruct goods :id :price)

;; generate data.
(def data (map #(struct goods %1 %2)
	       (shuffle (range 0 10)) (shuffle
				       (into (range 100 500 100)
					     (range 100 500 100)))))

(defn comp-goods-price
  "a compare function by :price for struct 'goods'; 
  if the :price is same, compare by :id."
  [el1 el2]
  (or (< (:price el1) (:price el2))
      (and (= (:price el1) (:price el2)) (< (:id el1) (:id el2)))))

user> data
({:id 1, :price 300} {:id 6, :price 100} {:id 3, :price 100} {:id 4, :price 400} {:id 0, :price 300} {:id 2, :price 200} {:id 5, :price 200} {:id 8, :price 400})
user> (sort (comp comp-goods-price) data)
({:id 3, :price 100} {:id 6, :price 100} {:id 2, :price 200} {:id 5, :price 200} {:id 0, :price 300} {:id 1, :price 300} {:id 4, :price 400} {:id 8, :price 400})
user> (sort-by :price < data) ; compare this with the above.
({:id 6, :price 100} {:id 3, :price 100} {:id 2, :price 200} {:id 5, :price 200} {:id 1, :price 300} {:id 0, :price 300} {:id 4, :price 400} {:id 8, :price 400})


