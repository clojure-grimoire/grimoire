;; we have a list of key colours
;; We want to find the one closest to a supplied colour
;; We're storing rgb values as [r g b]
;; use min-key to find colour that minimizes 
;; the euclidean distance between the supplied colour 
;; and each key colour
;; thanks to rhudson, raek and mfex on #clojure

(defn distance-squared [c1 c2]
  "Euclidean distance between two collections considered as coordinates"
  (->> (map - c1 c2) (map #(* % %)) (reduce +)))

(def key-colours
     {[224 41 224] :purple
      [24 180 46] :green
      [12 129 245] :blue
      [254 232 23] :yellow
      [233 233 233] :white
      [245 27 55] :red
      [231 119 41] :orange
      })

(defn rgb-to-key-colour
  "Find colour in colour map closest to the supplied [r g b] triple"
  [rgb-triple colour-map]
  (colour-map
   (apply min-key (partial distance-squared rgb-triple) (keys colour-map))))

user=> (rgb-to-key-colour [255 0 0] key-colours)
:red
