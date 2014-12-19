#!/bin/bash
# This script serves to clone in and install the listed git repos on
# the notes and doc paths.

NOTES=notes-store
DOCS=doc-store

mkdir dat    # dir for git repos to live in
mkdir $DOCS  # dir used by Grimoire as the doc tree
mkdir $NOTES # dir used by Grimoire as the notes tree

_install_repo() {
    # Should not be directly used
    t="dat/$2/"
    if [ ! -d "$t" ];
    then
	git clone git@github.com:$2.git "$t"
    else
	pushd "$t"
	git pull origin master
    fi

    mkdir -p $PWD/$1/$3/

    f="$PWD/$1/$3/$4"
    if [ ! -f "$f" ]
    then
	ln -s "$PWD/dat/$2/$3/$4" "$f"
    fi
}

install_docs() {
    # $1 is a GitHub repo specifier ala user/repo without the .git extension
    # $2 is a group name
    # $3 is the artifact name
    _install_repo $DOCS $1 $2 $3
}

install_notes() {
    # args are the same as install_docs, however this function
    # installs to the notes dir not to the docs dir.
    _install_repo $NOTES $1 $2 $3
}

# Import the clojure.core docs & notes
#############################################################
install_docs  clojure-grimoire/doc-clojure-core  org.clojure clojure
install_notes clojure-grimoire/note-clojure-core org.clojure clojure
