;;; map destructuring, all features
user=>
(let [
      ;;Binding Map
      {:keys [k1 k2]        ;; bind vals with keyword keys
       :strs [s1 s2]        ;; bind vals with string keys
       :syms [sym1 sym2]    ;; bind vals with symbol keys
       :or {k2 :default-kw, ;; default values
            s2 :default-s, 
            sym2 :default-sym} 
       :as m}  ;; bind the entire map to `m`
      ;;Data
      {:k1 :keyword1, :k2 :keyword2,  ;; keyword keys
       "s1" :string1, "s2" :string2,  ;; string keys
       'sym1 :symbol1,                ;; symbol keys
       ;; 'sym2 :symbol2              ;; `sym2` will get default value
       }] 
  [k1 k2 s1 s2 sym1 sym2 m])  ;; return value

[:keyword1, :keyword2, 
 :string1, :string2,
 :symbol1, :default-sym, ;; key didn't exist, so got the default
 {'sym1 :symbol1, :k1 :keyword1, :k2 :keyword2, 
  "s1" :string1, "s2" :string2}]

;; remember that vector and map destructuring can also be used with 
;; other macros that bind variables, e.g. `for` and `doseq`