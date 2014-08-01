;;this is with liberator
;;branching on request method
(defresource my-resource
  :exists? (fn [{:keys [db] {query-params :query-params 
                             body :body 
                             method :request-method} 
                 :request}]
             
             (condp = method
               :get (my-get-exists-fn)
               :post (my-post-exists-fn))))