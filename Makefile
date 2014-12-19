cheatsheet:
	bash cheatsheet.sh

build: cheatsheet

test: build
	lein serve

deploy:
	git push deployment master

setup: cheatsheet
	mkdir dat        # dir for git repos to live in
	mkdir doc-store  # dir used by Grimoire as the doc tree
	mkdir note-store # dir used by Grimoire as the notes tree

# Import the clojure.core docs
#############################################################
	git clone git@github.com:clojure-grimoire/doc-clojure-core.git dat/doc-clojure-core
	mkdir -p doc-store/org.clojure
	ln -s $PWD/dat/doc-clojure-core $PWD/doc-store/org.clojure/clojure

# import the clojure.core notes
#############################################################
	git clone git@github.com:clojure-grimoire/note-clojure-core.git dat/note-clojure-core
	mkdir -p note-store/org.clojure
	ln -s $PWD/dat/doc-clojure-core $PWD/note-store/org.clojure/clojure
