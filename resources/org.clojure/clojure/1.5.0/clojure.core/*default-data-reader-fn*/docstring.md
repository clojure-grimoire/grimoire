  When no data reader is found for a tag and *default-data-reader-fn*
  is non-nil, it will be called with two arguments,
  the tag and the value.  If *default-data-reader-fn* is nil (the
  default), an exception will be thrown for the unknown tag.