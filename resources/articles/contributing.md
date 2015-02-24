---
layout: page
title: Contributing
---

Grimoire was designed to be built and maintained by taking changes in
the form of pull requests, and is hosted
[here on GitHub](https://github.com/arrdem/grimoire).

## Building & Running Grimoire

Grimoire is built as a matching pair of programs: a "database"
generator which users should not re-run and a webserver which uses the
filesystem database described above to generate and serve a
website. The generator program,
[lein-grim](https://github.com/clojure-grimoire/grimoire) is hosted
separately on GitHub. A shared library,
[lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire) for
reading and writing the various Grimoire supported datastores
including the Grimoire REST API is also available.


#### Requirements

- GNU Awk (MacOSX only)
- GNU Head (MacOSX only)
- Pygments
- Perl
- Leiningen (-> Maven) Git


#### Usage

```
$ git clone git@github.com:clojure-grimoire/grimoire.git grimoire
$ cd grimoire
$ make setup
```

The `make setup` command will use the various `git-clone` wrapper
scripts packaged with Grimoire to clone down the projects housing
Grimoire's documentation data and "install" them such that they will
be visible to the web server when it is booted.

`lein serve [portnum]` will run the server, against the datafiles in
`./doc-store/` and `./notes-store/`.

The provided `cheatsheet.sh` script automatically clones, builds and
patches [a fork](https://github.com/arrdem/clojure-cheatsheets) of
[AndyF's Cheatsheet](https://github.com/jafingerhut/clojure-cheatsheets).

The script `dat.sh` provides wrappers around `git-clone` as mentioned
above for cloning down and installing repositories containing
documentation trees for Grimoire.

## Submitting changes

Grimoire strictly follows the
[git-flow](http://nvie.com/posts/a-successful-git-branching-model/)
branching model. PRs against `master` will not be accepted. All prs
should target the `develop` branch. Note that the generated editing
and example addition links provide this behavior already.

## Legal

Grimoire is developed and maintained under the Eclipse Public License
1.0 as noted on the [about page](/about).
