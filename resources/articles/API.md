---
layout: page
title: API Documentation
---

Grimoire hosts a lot of information about symbols, namespaces and packages in
the Clojure ecosystem, not all of which was easy to get in one place. In the
hope that others will be able to make use of this information, a relatively
programmer friendly API is provided for interacting with Grimoire as a datastore
using either JSON or EDN as an interchange format. This document serves to
sketch the Grimoire API as it appears to programmers, however the API is
designed to be largely self-enumerating so experimentation is highly encouraged.

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

**Note:** Individuals wanting to interact with Grimoire are strongly advised to
take advantage of
[lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire) rather than
duplicating this function.

`$VERSION` is a string representing a [SemVer](http://semver.org/) version,
however the formatting is not enforced and qualfiers such as `-alpha4` are
supported.

`$NAMESPACE` is the cannonical name of a Clojure namespace with no munging
applied.

## HTTP Interface

For user access to much of this data, Grimoire 0.4.7 Uses the following link
structure for serving HTML and plaintext markup documentation:

The path `/store/v0/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/` for
symbol being either a def or a known special form will yield a HTML page
representing either a failure to find documentation (404) or a HTML page
containing all available notes, examples and possibly other content. If the type
"text/plain" is provided either as a GET paramenter to `type`, or as a request
type, these paths will yield plain text rather than HTML results.

The paths
`/store/v0/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/docstring`,
`/store/v0/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring`,
`/store/v0/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/related` and
`/store/v0/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/examples` are also
defined for all defs and special forms. These resources will only ever yield
plain text results.

Clients connecting to Grimoire via HTTP for HTML or text results via the
`/store/` structure are expected to be able to handle 30X redirects. See the
compatibility section for notes on provided support for legacy URLs and
redirects.

### Examples

`GET http://conj.io/store/v0/org.clojure/clojure/1.6.0/clojure.core/conj/ TYPE: text/html`
shall return HTML formatted documentation of `clojure.core/conj` as
of the release of `[org.clojure/clojure "1.6.0"]` as per convention.

`GET http://conj.io/store/v0/org.clojure/clojure/1.6.0/clojure.core/conj/ TYPE: text/plain`
shall return plain text or un-rendered markdown documentation of
`clojure.core/conj` as of `[org.clojure/clojure "1.6.0"]` formatted for an
80-character display.

`GET http://conj.io/store/v0/org.clojure/clojure/1.6.0/clojure.core/conj/?type=text/plain`
shall give the same result as if the type header were set to `text/plain`.

### Searching

Some limited searching capabilities are privided by the path
`/search/v1/$PLATFORM/$NAMESPACE/$SYMBOL/`. Clients hitting this URL should
expect to get a HTTP redirect to a page representing the newest known version of
the artifact containing the designated namespace. Some clients including the
Emacs URL client are specially handled and the redirect will be handled on the
server side.

### Compatibility

Note that prior to Grimoire 0.4.0, the path prefix
`/store/v0/$ARTIFACTID/$GROUPID` was not required. In the interest of preserving
links and escaping link rot, all versioned Grimoire URLs will be supported going
forwards indefinitely. Existing URLs from Grimoire 0.1-0.3 of the
`$VERSION/$NS/$DEF` schema are supported, and compatability is provided for
Grimoire 0.4 `/store/$GROUP/$ARTIFACT/$VERSION/$NS/$DEF` URLs. Both of these
schemas are deprecated and supported via 301 'moved permanently'
redirects. Clients are expected to be able to follow such redirects.

## JSON/EDN API

While Grimoire 0.3.X lacked a API traversing the backing datastore, Grimoire 0.4
and later resolve that limitation adding a HTTP API with support for both JSON
and EDN responses.

As of the writing of this document, the JSON/EDN API is at version `v1`. The API
will still respond to `v0` calls, however `v0` is limited to viewing Clojure
("clj") namespaces and defs, and as of this writing generates API `v1` and store
`v0` URLs. No API `v0` URLs are generated, and all such URLs should be
considered deprecated.

All routes in the API return an object, representing either success or failure.

In JSON:
```javascript
{"result": "success",
 "body":   ...}

{"result": "failure",
 "body":   ...}
```

In EDN:
```Clojure
{:result :success,
 :body   ...}

{:result :failure
 :body   ...}
```

The exact content of each body is documented below. Note that a full
client for this API is provided as part of
[lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire).

Rather than forging their own request URLs, clients are expected to traverse the
URLs enumerated in API results. These URLs while they may have an obvious schema
at the current version are not guaranteed in future versions and will always
point to the same data accessed via the latest API version. However 

## `http://conj.io/api/v1?op=groups`

Succeeds returning a list of records representing the various groups
known to Grimoire.

In JSON:
```javascript
{"result": "success",
 "body"  : [{"name":     "/org.clojure",
             "html":     "/store/v0/org.clojure",
             "children": {"notes":     "/api/v1/org.clojure?op=notes",
                          "meta":      "/api/v1/org.clojure?op=meta",
                          "artifacts": "/api/v1/org.clojure?op=artifacts"}},
             ...]}
```

## `http://conj.io/api/v1/$GROUP?op=notes`

Succeeds returning a simple string, being markdown formatted but
unrendered notes about the group in question.

Fails only if there is no such `$GROUP`. A group with no notes will
succeed with the empty string as its result.

In JSON:
```javascript
{"result": "success",
 "body"  : "some notes"}
```

Note that `?op=notes` is defined over every path in this API except
for the base path, so further mention of it is elided.

## `http://conj.io/api/v1?op=ns-resolve&ns=$NAMESPACE`

Succeeds returning an augmented namespace result indicating the newest
documented artifact with the namespace in question. The `GET` parameter
`platform=` may be set to any supported platform, otherwise "clj" will be used
by default.

Fails if the given platform/namespace pair isn't known.

```javascript
{"result": "success",
 "body":   {"namespace": "clojure.core",
            "version":   "1.7.0-alpha4",
            "artifact":  "clojure",
            "group":     "org.clojure",
            "html":      "/store/v0/org.clojure/clojure/1.7.0-alpha4/clojure.core",
            "children":  {"all": "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=all",
                          "macros":    "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=macros",
                          "vars":      "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=vars",
                          "fns":       "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=fns",
                          "specials":  "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=specials",
                          "sentinels": "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=sentinels",
                          "notes":     "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=notes",
                          "meta":      "/api/v1/org.clojure/clojure/1.7.0-alpha4/clojure.core?op=meta"}}}
```

## `http://conj.io/api/v1/$GROUP?op=meta`

Succeeds returning a map, being the metadata known about the group in
question, or the empty map if nothing is known.

Fails only if there is no such `$GROUP`. A group with no notes will
succeed with the empty string as its result.

Note that `?op=meta` is defined over every path in this API except the
base path, so further mentions of it are elided pending documentation
of the metadata which may be expected from different resources.

## `http://conj.io/api/v1/$GROUP?op=artifacts`

Succeeds returning a list of records representing the various
artifacts in a known group.

Fails only if there is no such `$GROUP`. An empty group will succeed
returning an empty body.

In JSON:
```javascript
{"result": "success",
 "body"  : [{"name":     "org.clojure/clojure",
             "html":     "/store/v0/org.clojure/clojure",
             "children": {"notes":    "/api/v1/org.clojure/clojure?op=notes",
                          "meta":     "/api/v1/org.clojure/clojure?op=meta",
                          "versions": "/api/v1/org.clojure/clojure?op=versions"}},
             ...]}
```

## `http://conj.io/api/v1/$GROUP/$ARTIFACT?op=versions`

Succeeds returning a list of records representing the known versions
of a the artifact in question.

Fails only if there is no such artifact or group.

In JSON:
```javascript
{"result": "success",
 "body"  : [{"name":     "org.clojure/clojure/1.6.0",
             "html":     "/store/v0/org.clojure/clojure/1.6.0",
             "children": {"notes":      "/api/v1/org.clojure/clojure/1.6.0?op=notes",
                          "meta":       "/api/v1/org.clojure/clojure/1.6.0?op=meta",
                          "platforms":  "/api/v1/org.clojure/clojure/1.6.0?op=platforms"}},
             ...]}
```

## `http://conj.io/api/v1/$GROUP/$ARTIFACT/$VERSION?op=platforms`

Succeeds returning a list of records representing the platforms for which the
given version contains namespaces. Supported platforms include
`#{"clj" "cljs" "cljclr" "kiss" "ox" "toc"}`.
This list is not exhaustive and may grow.

Fails if the version, artifact or group does not exist.

In JSON:
```javascript
{"result": "success",
 "body"  : [{"name":     "org.clojure/clojure/1.6.0/clj",
             "html":     "/store/v0/org.clojure/clojure/1.6.0/clj",
             "children": {"notes":    "/api/v1/org.clojure/clojure/1.6.0/clj?op=notes",
                          "meta":     "/api/v1/org.clojure/clojure/1.6.0/clj?op=meta",
                          "macros":   "/api/v1/org.clojure/clojure/1.6.0/clj?op=namespaces"}},
             ...]}
```

## `http://conj.io/api/v1/$GROUP/$ARTIFACT/$VERSION/$PLATFORM?op=namespaces`

Succeeds returning a list of records representing all the namespaces that the
given artifact contains for the specified platform.

Fails if the platform, version, artifact, group does not exist.

In JSON:
```javascript
{"result": "success",
 "body"  : [{"name":     "org.clojure/clojure/1.6.0/clj/clojure.core",
             "html":     "/store/v0/org.clojure/clojure/1.6.0/clj/clojure.core",
             "children": {"notes":     "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=notes",
                          "meta":      "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=meta",
                          "all":       "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=all",
                          "macros":    "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=macros",
                          "vars":      "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=vars",
                          "fns":       "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=fns",
                          "specials":  "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=specials",
                          "sentinels": "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core?op=sentinels"}},
             ...]}
```

## `http://conj.io/api/v1/$GROUP/$ARTIFACT/$VERSION/$PLATFORM/$NS?op=all`

Succeeds returning a list of records representing all the defs and
sentinels in the given namespace.

Fails if the namespace, version, artifact or group does not exist.

In JSON:
```javascript
{"result": "success",
 "body"  : [{"name":     "org.clojure/clojure/1.6.0/clj/clojure.core/concat",
             "html":     "/store/v0/org.clojure/clojure/1.6.0/clj/clojure.core/concat",
             "children": {"notes":    "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core/concat?op=notes",
                          "meta":     "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core/concat?op=meta",
                          "examples": "/api/v1/org.clojure/clojure/1.6.0/clj/clojure.core/concat?op=examples"}},
             ...]}
```

The ops, `macros`, `vars`, `fns`, `specials` and `sentinels` are
simply type selectors which return distinct subsets of thes `all`
results.

## `http://conj.io/api/v1/$GROUP/$ARTIFACT/$VERSION/$PLATFORM/$NS/$SYMBOL?op=examples`

Succeeds returning a list of examples for this version of the symbol
as strings of plain text. Note that this does not return all examples
for all prior versions.
