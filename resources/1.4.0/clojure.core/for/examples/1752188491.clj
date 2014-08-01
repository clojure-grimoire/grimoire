(defn all-files-present?
"Takes a list of real file names, and returns a map of files present 1
and not present 0."
[file-seq]
(for [fnam file-seq
 :let [stat-map {(keyword fnam) (look-for fnam "f")}]]
  stat-map))

(into {}  (all-files-present? '("Makefile" "build.sh" "real-estate.csv")))

{:Makefile 1, :build.sh 1, :real-estate.csv 0}