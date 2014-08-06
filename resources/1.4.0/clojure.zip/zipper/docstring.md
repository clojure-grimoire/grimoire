  Creates a new zipper structure. 

  branch? is a fn that, given a node, returns true if can have
  children, even if it currently doesn't.

  children is a fn that, given a branch node, returns a seq of its
  children.

  make-node is a fn that, given an existing node and a seq of
  children, returns a new branch node with the supplied children.
  root is the root node.