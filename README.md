grimoire
=================
> "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."

See [grimoire in action](http://grimoire.arrdem.com/).
Learn more [about grimoire](http://grimoire.arrdem.com/about/).
And [contribute to grimoire](http://grimoire.arrdem.com/contributing/).

Developer Notes
===============

To serve up a local copy of grimoire, install [jekyll](http://jekyllrb.com/): `gem install
jekyll`. To run a server which translates the templates to html (there's some lag as
there are a number of templates to build): `jekyll serve -V`.

To rebuild most of the site e.g. examples and index page : `lein run`

To build the cheatsheet home page:

```sh
$ git clone https://github.com/jafingerhut/clojure-cheatsheets
$ cd clojure-cheatsheets/src/clj-jvm
$ lein run links-to-grimoire
$ cd -
$ cp clojure-cheatsheets/src/clj-jvm/cheatsheet-full.html .
$ perl -i -p -e 's%http://grimoire.arrdem.com%{{ site.baseurl }}%g' cheatsheet-full.html
$ perl -i -p -e 's%/1.6.0%{{ site.clojure_version }}%g' cheatsheet-full.html
$ perl -i -p -e 's%cheatsheet_files%{{ site.baseurl }}public%g' cheatsheet-full.html
$ mv -f cheatsheet-full.html _includes/cheatsheet.html
```
