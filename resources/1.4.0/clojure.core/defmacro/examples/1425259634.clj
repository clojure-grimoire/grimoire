(defmacro with-tree [tree & body]
  "works on a JTree and restores its expanded paths after executing body"
  `(let [tree# ~tree
         root# (.getRoot (.getModel tree#))
         expanded# (if-let [x# (.getExpandedDescendants
                                tree# (TreePath. root#))]
                     (enumeration-seq x#)
                     ())
         selectionpaths# (. selectionmodel# getSelectionPaths)]
     ~@body
     (doseq [path# expanded#]
       (.expandPath tree# path#))))

;; usage:

(with-tree *one-jtree-instance*
   ;; some code here...
  )