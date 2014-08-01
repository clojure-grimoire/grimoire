---
layout: page
title: API Documentation
---

## Notation

`$SYMBOL` as used in this document is a string representing the name
of an intered and documented var munged as follows:

```Clojure
(defn munge [s]
  (-> s
      (replace "?" "_QMARK_")
      (replace "." "_DOT_")
      (replace "/" "_SLASH_")
      (replace #"^_*" "")
      (replace #"_*$" "")))
```

`$VERSION` is a Clojure version, one of `#{"1.6.0", "1.5.0", "1.4.0"}`
unless otherwise specified.

`$NAMESPACE` is the cannonical name of a Clojure namespace with no
munging applied.

## Filesystem

As of 0.3.0, Grimoire is built atop a filesystem "database" consisting
of the following resource paths

```
/resources/$VERSION/release-notes.md
/resources/$VERSION/$NAMESPACE/ns-notes.md
/resources/$VERSION/$NAMESPACE/$SYMBOL/
/resources/$VERSION/$NAMESPACE/$SYMBOL/name.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/type.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/arities.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/docstring.md
/resources/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.md
/resources/$VERSION/$NAMESPACE/$SYMBOL/source.clj
/resources/$VERSION/$NAMESPACE/$SYMBOL/related.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj
```

This list of paths is provided as a reference for developers hacking
on Grimoire and should not be considered stable or user-accessible.

## HTTP API

For user access to much of this data, Grimoire 0.3.0 introduces a HTTP
based API for requesting some data.

`/$VERSION/$NAMESPACE/$SYMBOL/index.html` shall be the result of
formatting all other API resources related to a single symbol save
`/index.txt` into a single syntax highlighted HTML document.

`/$VERSION/$NAMESPACE/$SYMBOL/index.txt` shall be the result of
formatting all other API resources save `/index.html` into a single
file with no highlighting or markup. There may be plain text
formatting, and the result may be wrapped at 80 characters of width.

The paths `/$VERSION/$NAMESPACE/$SYMBOL/docstring`,
`/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring`,
`/$VERSION/$NAMESPACE/$SYMBOL/related` and
`/$VERSION/$NAMESPACE/$SYMBOL/examples` are also defined for all
symbols and special forms. These resources will only ever yield plain
text results.

## HTTP Examples

`GET /1.6.0/clojure.core/conj/ TYPE: text/html` shall return
HTML formatted documentation of `clojure.core/conj` as per convention.

`GET /1.6.0/clojure.core/conj/ TYPE: text/plain` shall return plain
text or un-rendered markdown documentation of `clojure.core/conj`
formatted for an 80-character display.

`GET /1.6.0/clojure.core/conj/?type=text/plain` shall give the same
result as if the type header were set to `text/plain`.
