  Returns a sorted sequence of the items in coll, where the sort
  order is determined by comparing (keyfn item).  If no comparator is
  supplied, uses compare.  comparator must implement
  java.util.Comparator.  If coll is a Java array, it will be modified.
  To avoid this, sort a copy of the array.