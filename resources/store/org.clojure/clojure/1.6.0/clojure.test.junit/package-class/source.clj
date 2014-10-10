(defn package-class
  [name]
  (let [i (.lastIndexOf name ".")]
    (if (< i 0)
      [nil name]
      [(.substring name 0 i) (.substring name (+ i 1))])))