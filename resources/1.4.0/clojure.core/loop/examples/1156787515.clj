; makes a simple template function that can be used in mustache way: http://mustache.github.com/
(defn template [tpl env]
  (loop [tpl tpl
         env env]
    (cond (empty? env)
          tpl
          :else
          (let [[key value] (first env)]
            (recur (try (clojure.string/replace tpl 
                                                (re-pattern (str "\\{\\{" (name key) "\\}\\}")) 
                                                value)
                        (catch Exception e tpl)) 
                   (rest env))))))