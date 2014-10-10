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

As of 0.3.10, Grimoire is built atop a filesystem "database" consisting
of the following resource paths

```
/resources/store/$GROUPID/
/resources/store/$GROUPID/group-notes.md
/resources/store/$GROUPID/$ARTIFACTID/
/resources/store/$GROUPID/$ARTIFACTID/artifact-notes.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/release-notes.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/ns-notes.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/name.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/type.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/arities.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/docstring.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/source.clj
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/related.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/examples/
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj
```

This list of paths is provided as a reference for developers hacking
on Grimoire and should not be considered stable or user-accessible
yet.

The goal of this structure is to keep the documentation for different
artifacts isolated, hopefully enabling documentation to be split out
of the core Grimoire repo at some time in the future.

## HTTP API

For user access to much of this data, Grimoire 0.3.0 introduces a HTTP
based API for requesting some data.

`/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/index.html` shall be the result of
formatting all other API resources related to a single symbol save
`/index.txt` into a single syntax highlighted HTML document.

`/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/index.txt` shall be the result of
formatting all other API resources save `/index.html` into a single
file with no highlighting or markup. There may be plain text
formatting, and the result may be wrapped at 80 characters of width.

The paths `/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/docstring`,
`/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring`,
`/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/related` and
`/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/examples` are also defined for all
symbols and special forms. These resources will only ever yield plain
text results.

## HTTP Examples

`GET /store/org.clojure/clojure/1.6.0/clojure.core/conj/ TYPE:
text/html` shall return HTML formatted documentation of
`clojure.core/conj` as of the release of
`[org.clojure/clojure "1.6.0"]` as per convention.

`GET /store/org.clojure/clojure/1.6.0/clojure.core/conj/ TYPE:
text/plain` shall return plain text or un-rendered markdown
documentation of `clojure.core/conj` as of
`[org.clojure/clojure "1.6.0"]` formatted for an 80-character display.

`GET /store/org.clojure/clojure/1.6.0/clojure.core/conj/?type=text/plain` shall give the same
result as if the type header were set to `text/plain`.

## Compatibility

Note that prior to Grimoire 0.3.10, the prefix
`/store/$ARTIFACTID/$GROUPID` was not required. Consequently until the
0.4.0 release of Grimoire URLs with a valid `$VERSION` as the first
element will be redirected to add the prefix
`store/org.clojure/clojure/`.
