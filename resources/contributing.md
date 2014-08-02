---
layout: page
title: Contributing
---

Grimoire was designed to be built and maintained by taking changes in
the form of pull requests, and is hosted
[here on GitHub](https://github.com/arrdem/grimoire).

## File structure

Each function, variable and macro and special form has the following files:

```
/resources/$VERSION/$NAMESPACE/$SYMBOL/arities.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/docstring.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/source.clj
/resources/$VERSION/$NAMESPACE/$SYMBOL/related.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj
```

These files have no markup, HTML or formatting beyond that possible in
plain text and serve double duty as the sources from which the main
pages are generated and the plain text "API" provided by Grimoire 0.3.X.

## Writing Examples

Examples are simply raw Clojure REPL sessions with the .clj extension
dumped to files. As with other resources in the "API", these should be
simple UTF-8 plain text files with no markup. To add an example,
simply create a REPL dump file and add it to the appropriate examples
directory. Nothing else is involved.

**Protip**, if you visit the documentation page of the symbol for
which you wish to add an example, there are big friendly "add example"
links which will help you to create a file in the right place.

REPL sessions are expected to fit the pattern `("user=>" sexpr) +`.
Results of evaluation should be commented out, as should output to any
file stream such as stdout or stderr. This is required in the
interests of providing automatic example analysis in the future
although it may not be reflected in current examples.

## Building & Running Grimoire

Grimoire is built as a matching pair of programs: a "database"
generator which users should not re-run and a webserver which uses the
filesystem database described above to generate and serve a website.

`lein with-profile server run` will run the server, against existing
datafiles.


## Legal

Grimoire is &copy; to Reid Mckenzie 2014. All rights reserved.

Contributors release examples, commentary, documentation, code and
other content submitted to this project to the sole ownership of Reid
McKenzie as above.
