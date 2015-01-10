[![Latest release](http://img.shields.io/github/tag/clojure-grimoire/grimoire.svg)](http://conj.io/)
[![Gittip button](http://img.shields.io/gittip/arrdem.svg)](https://www.gittip.com/arrdem/ "Support this project")

Grimoire
=================

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/clojure-grimoire/grimoire?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

> "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."

Grimoire is community web documentation of the Clojure programming
language intended to provide an up to date replacement for ClojureDocs
by documenting current versions of Clojure and to augment Clojure's
documentation by hosting examples and extended docstrings.

See [grimoire in action](http://conj.io).
Learn more [about grimoire](http://conj.io/about).
Read the [road map](http://www.arrdem.com/2014/07/12/of_mages_and_grimoires/).
And [contribute to grimoire](http://conj.io/contributing).

## Usage

If you want to run a local instance of Grimoire rather than using the
hosted version, the following should get you started:

```
$ git clone git@github.com:clojure-grimoire/grimoire.git grimoire
$ cd grimoire
$ make setup
$ lein serve
```

Note that Grimoire is _not_ especially svelt, and has stuff like
Google Analytics baked in which will still be present when running
locally. A "lightweight" client based off of
[lib-grimoire](https://github.com/clojure-grimoire/lib-grimoire) is in
the works.

## License

Grimoire itself is copyright Reid "arrdem" McKenzie 2014, published
under the EPL for your enjoyment.

Data files used by Grimoire are under various licenses as dictated by
their authors, no claim of ownership is made.
