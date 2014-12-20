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
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/name.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/type.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/arities.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/docstring.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.md
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/source.clj
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/related.txt
/resources/store/$GROUPID/$ARTIFACTID/$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj
```

 - **name.txt** is the unmunged name of the symbol.
 - **type.txt** notes the type (macro, function, special form, var) of the symbol.
 - **arities.txt** lists the official arities of the form.
 - **docstring.md** is the official documentation of the form if
   any. note that while it's in a markdown file it isn't rendered for
   markdown formatting. No changes will be taken to these files.
 - **source.clj** is the Clojure source for the function or symbol in
   question if any. No changes will be taken to these files.
 - **extended-docstring.md** is for user contributed "extended"
   documentation. This is where additonal commentary and gotchas
   belong.
 - **related.txt** is a newline delimited list of fully qualified
   symbols related to the documented symbol.

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

`lein serve [portnum]` will run the server, against the datafiles in
`resources/`. Note that Grimoire cannot currently be built to an
uberjar due limitations of `clojure.java.io/resource` which have not
yet been worked around.

## Submitting changes

Grimoire strictly follows the
[git-flow](http://nvie.com/posts/a-successful-git-branching-model/)
branching model. PRs against `master` will not be accepted. All prs
should target the `develop` branch. Note that the generated editing
and example addition links provide this behavior already.

## Legal

Please note the license under which Grimoire is developed as noted in
the [about page](/articles/about).
