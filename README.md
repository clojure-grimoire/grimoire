[![Latest release](http://img.shields.io/github/tag/clojure-grimoire/grimoire.svg)](http://conj.io/)
[![Gittip button](http://img.shields.io/gittip/arrdem.svg)](https://www.gittip.com/arrdem/ "Support this project")

Grimoire
=================

> "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."

Grimoire is community web documentation of the Clojure programming language intended to provide an up to date replacement for ClojureDocs by documenting current versions of Clojure and to augment Clojure's documentation by hosting examples and extended docs.

 - See [grimoire in action](http://conj.io)!
 - Learn more [about grimoire](http://conj.io/about)!
 - Check out [the API](http://conj.io/api)!
 - [Contribute to grimoire](http://conj.io/contributing).
 - Read the [original post](http://www.arrdem.com/2014/07/12/of_mages_and_grimoires/)!

### Official Clients

These are clients and tools built as part of the Grimoire project and maintained as part of normal Grimoire development.

| Client     | Link          |
|:-----------|:--------------|
| [$BROWSER] | [grimoire](http://conj.io) |
| [Clojure]  | [lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire) |

### Supported Clients

These are clients which while not built or maintained as part of the Grimoire project do get support and updates as Grimoire evolves.

| Client      | Link          |
|:----------- |:--------------|
| Emacs       | [CIDER](https://github.com/clojure-emacs/cider/) via `cider-grimoire`, `cider-grimoire-web` |
| Light Table | [Sancho](https://github.com/cldwalker/Sancho) |
| Vim         | [grimoire.vim](https://github.com/jebberjeb/grimoire.vim) |

If you develop a new client for Grimoire, let me know and I'll do my best to support it as well.
PRs to add clients to this list are welcome.

## Local Instance

If you want to run a local instance of Grimoire rather than using the hosted version or if you just want to hack on Grimoire, the following should get you started:

Requires:

 - GNU Awk (MacOSX only)
 - GNU Head (MacOSX only)
 - Pygments
 - Perl
 - Leiningen (-> Maven)
 - Git

```
$ git clone git@github.com:clojure-grimoire/grimoire.git grimoire
$ cd grimoire
$ make setup
$ lein serve
```

Note that Grimoire is _not_ especially svelt, and has stuff like Google Analytics baked in which will still be present when running locally.
A "lightweight" client based off of [lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire) is in the works.

## License

Grimoire is in fact several projects operating under different licenses.

[Grimoire](https://github.com/clojure-grimoire/grimoire) itself (this website codebase) is copyright Reid "arrdem" McKenzie, published under the EPL for your enjoyment.

[lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire) (the API, datastore driver) is copyright Reid "arrdem" McKenzie, also published under the EPL.

[datastore](https://github.com/clojure-grimoire/datastore) (the notes and examples) is published under [CC0](https://creativecommons.org/publicdomain/zero/1.0/) with thanks to all contributors.

The source metadata files, being the original documentation, source and source information used by Grimoire are derived from various artifacts published under various licenses as dictated by their authors and as such no claim of ownership is made and the original license is reproduced.
