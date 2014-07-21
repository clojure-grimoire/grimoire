(ns grimoire.github)

(def ^:private prefix "https://github.com/")

(defn ->repo [user name]
  {:repo (str prefix user "/" name)
   :type :repo})

(defn ->file-url
  "Helper function for translating a repo and a file into a GitHub URL."

  ([{:keys [repo]} file]
     (str repo "tree/master/" file))

  ([{:keys [repo]} branch file]
     (str repo "tree/" branch "/" file)))

(defn ->edit-url
  "Helper function for translating a repo and a file into the edit
  link for a given file. If no branch is provided, then \"master\" is
  used as the default."

  ([repo file]
     (->edit-url repo "master" file))

  ([{:keys [repo]} branch file]
     (str repo "edit/" branch file)))

(defn ->line-url
  "Helper function for translating a Repo, a file, a tag and a line
  into a GitHub line link. Defaults to the \"master\" branch if no
  branch is given, but bitches about the fact that such links are
  likely to break."

  ([repo file line]
     (.write *err* "Warning! Linking against master branch is unstable!\n")
     (->line-url repo "master" file))

  ([{:keys [repo]} label file line]
     (str repo "blob/" label "/" file (format "#L%d" line))))
