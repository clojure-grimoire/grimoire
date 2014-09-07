(defn test-name
  [vars]
  (apply str (interpose "."
                        (reverse (map #(:name (meta %)) vars)))))