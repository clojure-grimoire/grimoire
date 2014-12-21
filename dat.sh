#!/bin/bash
# This script serves to clone in and install the listed git repos on
# the notes and doc paths.

NOTES=notes-store
DOCS=doc-store

mkdir dat    # dir for git repos to live in
mkdir $DOCS  # dir used by Grimoire as the doc tree

_install_repo() {
    # Should not be directly used
    t="dat/$2/"
    if [ ! -d "$t" ];
    then
        git clone git@github.com:$2.git "$t"
        #git clone https://github.com/$2.git "$t"
    else
        pushd "$t"
        git pull origin master
        popd
    fi

    mkdir -p "$PWD/$1/$3"
    src="$PWD/dat/$2/$3/$4"
    tgt="$PWD/$1/$3/$4"

    ln -s "$src" "$tgt"
}

install_docs() {
    # $1 is a GitHub repo specifier ala user/repo without the .git extension
    # $2 is a group name
    # $3 is the artifact name
    _install_repo $DOCS $1 $2 $3
}

# Install the notes
############################################################
# Note that the notes are a single monolithic project, whereas
# individual libraries are documented seperately.
git clone git@github.com:clojure-grimoire/datastore.git\
          dat/clojure-grimoire/notes
ln -s $PWD/dat/clojure-grimoire/notes $PWD/$NOTES

# Import the clojure.core docs & notes
############################################################
install_docs clojure-grimoire/doc-clojure-core org.clojure clojure
install_docs clojure-grimoire/doc-cljs-core    org.clojure clojurescript
install_docs clojure-grimoire/doc-core-async   org.clojure core.async
install_docs clojure-grimoire/doc-core-typed   org.clojure core.typed
install_docs clojure-grimoire/doc-core-typed   org.clojure core.typed.rt
