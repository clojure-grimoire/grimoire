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

## Building & Running Grimoire

Grimoire is built as a matching pair of programs: a "database" generator which users should not re-run and a webserver which uses the filesystem database described above to generate and serve a website.

### Requirements

- GNU Awk (MacOSX only)
- GNU Head (MacOSX only)
- Pygments
- Perl
- Leiningen (-> Maven) Git


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

## Submitting changes

Grimoire strictly follows the [git-flow](http://nvie.com/posts/a-successful-git-branching-model/) branching model.
PRs against `master` will not be accepted.
All prs should target the `develop` branch.
Note that the generated editing and example addition links provide this behavior already.

## License

Grimoire and related projects are developed and maintained under a variety of licenses as noted on the [about page](/about).
