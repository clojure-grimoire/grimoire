---
layout: page
title: Contributing
---

Grimoire was designed to be built and maintained by taking changes in
the form of pull requests, and is hosted
[here on GitHub](https://github.com/arrdem/grimoire).

### File structure

Each function, variable and macro and special form has the following files:

<pre>
/resources/$VERSION/$NAMESPACE/$SYMBOL/arities.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/docstring.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/source.clj
/resources/$VERSION/$NAMESPACE/$SYMBOL/related.txt
/resources/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj
</pre>

These files have no markup, HTML or formatting beyond that possible in
plain text and serve double duty as the sources from which the main
pages are generated and the plain text "API" provided by Grimoire
0.3.X.

### Writing Examples

Examples are simply raw Clojure REPL sessions with the .clj extension
dumped to files. As with other resources in the "API", these should be
simple UTF-8 plain text files with no markup. To add an example,
simply create a REPL dump file and add it to the appropriate examples
directory. Nothing else is involved.

REPL sessions are expected to fit the pattern `("user=>" sexpr) +`.
Results of evaluation should be commented out, as should output to any
file stream such as stdout or stderr. This is required in the
interests of providing automatic inter-var linking and other static
analysis.

### Building & Running Grimoire

Grimoire is built as a matching pair of programs: a "database"
generator which users should not re-run and a webserver which uses the
filesystem database described above to generate and serve a website.

`lein with-profile server run` will run the server, against existing
datafiles.
