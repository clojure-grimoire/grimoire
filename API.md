As of 0.3.0, Grimoire features an API consisting of the following
resource paths

```
/$VERSION/$NAMESPACE/$SYMBOL/arities
/$VERSION/$NAMESPACE/$SYMBOL/docstring
/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring
/$VERSION/$NAMESPACE/$SYMBOL/source
/$VERSION/$NAMESPACE/$SYMBOL/related
/$VERSION/$NAMESPACE/$SYMBOL/examples-list
/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID
```

$SYMBOL is a string representing the name of an intered and documented
var munged as follows:

```Clojure
(defn munge [s]
  (-> s
      (replace "?" "_QMARK_")
      (replace "." "_DOT_")
      (replace "/" "_SLASH_")
      (replace #"^_*" "")
      (replace #"_*$" "")))
```

All listed files are flat text with no markup, syntax highlighting or
HTML.
