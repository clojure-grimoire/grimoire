---
layout: page
title: Contributing
---

Grimoire was designed to be built and maintained by taking changes in the form of pull requests, and is hosted [here on GitHub](https://github.com/clojure-grimoire/grimoire).

The Grimoire project itself is now partitioned into several pieces:

- The site itself (the main repo)
- [lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire)
- [datastore](https://github.com/clojure-grimoire/datastore)
- [lein-grim](https://github.com/clojure-grimoire/lein-grim)

## Submitting Examples or Notes

To submit an example or a note, you can either use the embedded `edit` and `add` links on the Grimoire service itself to fork and edit documentation and examples via GitHub's web interface or you can fork [the project](https://github.com/clojure-grimoire/datastore) and edit it as you see fit.

### Writing an Example

In order to be accepted, examples must conform to [bbatsov/clojure-style-guide](https://github.com/bbatsov/clojure-style-guide) within reason.

Examples must be copy/pastable in full into a REPL with no editing.
This means that error messages, println output and soforth should be commented out.
Prompts such as `user>`, `user=>`, `λx.x>` and soforth should be omitted.

For instance

```Clojure
user=> 4
4
```

should be avoided, as the string `user=>` is a valid symbol which precludes pasting the text unmodified into a REPL.

```Clojure
4
;; => 4
```
would be better because it can be copy/pasted.

Prose comments detailing what is going on are encouraged prior to each statement or block.
Exceptions when deliberately encountered need not be handled.

For instance

```Clojure
(inc [1])
;; Ouch!
;; => ClassCastException clojure.lang.PersistentVector cannot be cast to java.lang.Number
```

Is totally OK.

### Adding an Example

Examples should be simple `foo.clj` files, added to the `/examples` subdirectory of the oldest version of the def to which the example applies.
For instance if I were to write an example of clj::clojure.core/for, I would do so by inspecting the `:added` metadata on the var

```Clojure
(print (meta #'clojure.core/for))
;; => {:arglists ([seq-exprs body-expr]),
;;     :added 1.0,
;;     :line 4444,
;;     :column 1,
;;     :file clojure/core.clj,
;;     :name for,
;;     :macro true}
```

The for macro is packaged in `[org.clojure/clojure "[1.0.0,)"]` since we know it was added in `1.0`.
As a result, we know that the clj::grimoire.things path will be `org.clojure/clojure/1.0.0/clj/clojure.core/for`.
Examples live in the `/examples` directory as mentioned before, and the file name of an example matters only for editing.
So if `$REPO` is the checkout location of the datastore project, our example needs to live in `$REPO/org.clojure/clojure/1.0.0/clj/clojure.core/for/example/foo.clj` or somewhere in that same directory.

If there is no `:added` metadata to guide our choice, simply adding the example to the latest version is acceptable.

### Writing Notes

Notes should fully replace the Clojure docstring.
The docstrings of Clojure, the various contributed libraries and other Clojure ecosystem projects use a bewildering array of ad-hoc plain text formatting, markup languages, unannotated Markdown and other notation.
Content issues such as typos, ambiguities and excessive terseness are also fairly common.
As a result, while the "official" documentation may be useful, in the general case it is not and the expectation should be that community documentation will where appropriate fully replace the official documentation rather than simply supplement it.

The best case in point of this is clj::clojure.core/gen-class, which becomes absolutely unusable when formatted automatically.
Other offenders include clj::clojure.pprint/cl-format and clj::clojure.instant/parse-timestamp . clj::clojure.core.typed/fn and many of the other core.typed functions also use their own markup.

Note files should be simple markdown as per the [common markdown](http://commonmark.org/) spec, and should use the spec to full effect including tables, links to appropriate blog posts and soforth.

One extension to this spec provided in Grimoire is "short things".
Frequently when writing documentation or notes you want to refer to some other def or namespace or what have you.
To date in this document and elsewhere I've used a strange notation, `<a>::<b>/<c>`, where `<a>` is usually `clj`, `<b>` is some namespace and `<c>` is something in that namespace.
This is what is referred to in lib-grimoire as a "short thing" see clj::grimoire.things/thing->short-string a function for making such strings (you can do it by hand too) and clj::grimoire.api/resolve-short-string a function for using database search to resolve such strings to Things again.
A good example of these in action is the notes for clj::clojure.core/defmulti, which makes extensive use of such implicit links.

### Adding Notes

Notes are simply files named `notes.md` in the directory representing any Thing.
Again assuming that `$REPO` is the checkout location of the datastore project, the file `$REPO/org.clojure/clojure/1.0.0/clj/clojure.core/for/notes.md` holds the notes for clj::clojure.core/for.
The file `$REPO/org.clojure/clojure/1.0.0/clj/clojure.core/unchecked-double/notes.md` holds the notes for clj::clojure.core/unchecked-double.
The file `$REPO/org.clojure/clojure/1.0.0/notes.md` would hold release notes for Clojure 1.0.
The file `$REPO/org.clojure/clojure/1.0.0/clj/notes.md` would hold notes on the Clojure code packaged in 1.0 (useful when there is both Clojure and ClojureScript in a single artifact).
The file `$REPO/org.clojure/clojure/1.0.0/clj/clojure.core/notes.md` holds the notes on the namespace clj::clojure.core

## Building & Running Grimoire

Grimoire is built as a matching pair of programs: a "database" generator which users should not re-run and a webserver which uses the filesystem database described above to generate and serve a website.

### Requirements

- GNU Awk
- GNU Head
- Pygments
- Perl
- Java 6 or later
- Leiningen
- Maven
- Git


### Usage

```
$ git clone git@github.com:clojure-grimoire/grimoire.git grimoire
$ cd grimoire
$ make setup
```

The `make setup` command will use the various `git-clone` wrapper scripts packaged with Grimoire to clone down the projects housing Grimoire's documentation data and "install" them such that they will be visible to the web server when it is booted.

The datastore repository will be cloned down and linked into the `./notes-store/` dir.
The various data repositories will be cloned down and linked into the `./doc-store/` dir.
Both of these directory trees share roughly the same structure as the Things described in the clj::grimoire.things documentation.

`lein serve [portnum]` will run the server, against the datafiles in `./doc-store/` and `./notes-store/`.

The included `cheatsheet.sh` script automatically clones, builds and patches [a fork](https://github.com/arrdem/clojure-cheatsheets) of [AndyF's Cheatsheet](https://github.com/jafingerhut/clojure-cheatsheets) and installs the resulting HTML for Grimoire to use.

# Submitting changes

Grimoire strictly follows the [git-flow](http://nvie.com/posts/a-successful-git-branching-model/) branching model.
PRs against `master` will not be accepted.
All prs should target the `develop` branch.
Note that the generated editing and example addition links provide this behavior already.

# License

Grimoire and related projects are developed and maintained under a variety of licenses as noted on the [about page](/about).
By submitting changes to this or another project you acknowledge the license of the project and release your contribution(s) under the same license.
