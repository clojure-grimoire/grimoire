---
layout: page
title: API Documentation
---

As of 0.3.0, Grimoire features an API consisting of the following
resource paths

```
/$version/$NAMESPACE/$SYMBOL/
/$VERSION/$NAMESPACE/$SYMBOL/name.txt
/$VERSION/$NAMESPACE/$SYMBOL/type.txt
/$VERSION/$NAMESPACE/$SYMBOL/arities.txt
/$VERSION/$NAMESPACE/$SYMBOL/docstring.md
/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.md
/$VERSION/$NAMESPACE/$SYMBOL/source.clj
/$VERSION/$NAMESPACE/$SYMBOL/related.txt
/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj
```

`$SYMBOL` is a string representing the name of an intered and
documented var munged as follows:

```Clojure
(defn munge [s]
  (-> s
      (replace "?" "_QMARK_")
      (replace "." "_DOT_")
      (replace "/" "_SLASH_")
      (replace #"^_*" "")
      (replace #"_*$" "")))
```

`/index.html` shall be the result of formatting all other API
resources save `/index.txt` into a single syntax highlighted HTML
document.

`/index.txt` shall be the result of formatting all other API resources
save `/index.html` into a single file with no highlighting or
markup. There may be plain text formatting, and the result may be
wrapped at 80 characters of width.

`GET /1.6.0/clojure.core/conj/ TYPE: text/plain` shall return `/index.txt`.

`GET /1.6.0/clojure.core/conj/ TYPE: text/html` shall return
`/index.html` as is the normal web convention.
