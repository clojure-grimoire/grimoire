---
layout: page
title: API Documentation
---

As of 0.3.0, Grimoire features a filesystem "API" consisting of the
following resource paths

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

Note that namespaces are not munged at all.

## File descriptions

`/name.txt` is the un-munged name of the symbol.

`/type.txt` is one of `#{"macro" "var" "fn" "special"}`

`/arities.txt` is an unformatted list of the function's arities.

`/docstring.md` is the official docstring of the function markdown formatted.

`/extended-docstring.md` is community documentation of the function markdown formatted.

`/source.clj` is the Clojure source for the given function or symbol.

`/related.txt` is a newline deliminated list of symbols in Clojure considered related.

# HTTP API

`/$VERSION/$NAMESPACE/$SYMBOL/index.html` shall be the result of
formatting all other API resources save `/index.txt` into a single
syntax highlighted HTML document.

`/$VERSION/$NAMESPACE/$SYMBOL/index.txt` shall be the result of
formatting all other API resources save `/index.html` into a single
file with no highlighting or markup. There may be plain text
formatting, and the result may be wrapped at 80 characters of width.

The paths `/$VERSION/$NAMESPACE/$SYMBOL/docstring/`,
`/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring/`,
`/$VERSION/$NAMESPACE/$SYMBOL/related/` and
`/$VERSION/$NAMESPACE/$SYMBOL/examples/` are also defined for all
symbols and special forms.

## HTTP Examples

`GET /1.6.0/clojure.core/conj/ TYPE: text/html` shall return
HTML formatted documentation of `clojure.core/conj` as per convention.

`GET /1.6.0/clojure.core/conj/ TYPE: text/plain` shall return plain
text or un-rendered markdown documentation of `clojure.core/conj`
formatted for an 80-character display.

`GET /1.6.0/clojure.core/conj/?type=text/plain` shall give the same
result as if the type header were set to `text/plain`.
