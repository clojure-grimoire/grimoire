#!/bin/bash
# This script serves to clone in and install the listed git repos on
# the notes and doc paths.

NOTES="$PWD/notes-store"
DOCS="$PWD/doc-store"

mkdir -p dat     # dir for git repos to live in
mkdir -p "$DOCS" # dir used by Grimoire as the doc tree

GIT_DAT="$PWD/dat"

[ -f `which gawk` ] && AWK=`which gawk` || AWK=`which awk`
echo "[debug] Using " $AWK " for awk imp'l"

git_get() {
    # This is a quick little git extension designed to allow me to
    # manage all the git repos that I keep around better. The idea is
    # that a "root" directory, $GIT_DAT/$OWNER/$REPO, and then symlink
    # that directory to wherever you are now as $REPO. Saves excessive
    # cloning and keeping multiple coppies of a single repo around.

    if ( [[ "--help" = $1 ]] ||
             [[ "help" = $1 ]] ||
             [[ -z $1 ]] )
    then
        echo "git-get"
        echo "Usage: git get [help | --help | GIT_ADDR | GIT_ADDR USER/NAME]"
        echo "  Clones a git repo to the $GIT_DAT dir, creating a symlink"
        echo "  into the current directory."
        exit 0
    elif [ ! -z $2 ]
    then
        USER=$(echo $2 | $AWK "{match(\$1,\"([^/]+)/.*\",a)}END{print(a[1])}")
        REPO=$(echo $2 | $AWK "{match(\$1,\".*?/(.*?)(.git)?\",a)}END{print(a[1])}")
    elif [ ! -z $1 ]
    then
        USER=$(echo $1 | $AWK "{match(\$1,\".*?:([^/]+)/.*\",a)}END{print(a[1])}")
        REPO=$(echo $1 | $AWK "{match(\$1,\".*?:[^/]+/(.*?).git\",a)}END{print(a[1])}")
    fi

    [ ! -z $USER ] && [ ! -z $REPO ] || exit 1

    # Ensure the target dir exists
    ([ -d "$GIT_DAT/$USER" ] || mkdir -p "$GIT_DAT/$USER" ) &&

        # Get the data
        ([ -d "$GIT_DAT/$USER/$REPO/.git/" ] || git clone $1 "$GIT_DAT/$USER/$REPO") &&

        # Create a link to it
        ln -s "$GIT_DAT/$USER/$REPO" "./$REPO" &&
        return 0
}

git_update() {
    # $1 is a repo dir
    pushd $1 > /dev/null
    (git reset --hard > /dev/null) &&
      (git pull --quiet origin master) &&
      (echo "Updated repo $1")
    popd > /dev/null
}

install_docs() {
    # $1 := GIT_GROUP
    # $2 := GIT_REPO
    # $3 := MVN_GROUP
    # $4 := MVN_ARTIFACT
    # Should not be directly used

    git_get "git@github.com:$1/$2.git"
    rm "./$2"
    dat="$GIT_DAT/$1/$2"
    src="$dat/$3/$4"
    tgt="$DOCS/$3/$4"

    if [ -d "$dat" ]
    then
        git_update "$dat"
    else
        git_get "git@github.com:$1/$2.git"
        rm "./$2"
    fi

    ( [ -d "$src" ] || ( echo "No such dir $src" && exit 1 ))

    mkdir -p "$DOCS/$3/"
    ([ -f "$tgt" ] && rm "$tgt" )
    ln -s "$src" "$tgt"
}

# Install the notes
############################################################
# Note that the notes are a single monolithic project, whereas
# individual libraries are documented seperately.
if [ -a "$NOTES" ]
then
    pushd "$NOTES" > /dev/null &&
    git reset --hard > /dev/null &&
    git pull --quiet &&
    echo "Updated datastore!" &&
    popd > /dev/null
else
    git_get git@github.com:clojure-grimoire/datastore.git &&
    mv "./datastore" "$NOTES"
fi

# Import the clojure.core docs & notes
############################################################
install_docs clojure-grimoire doc-clojure-core org.clojure clojure
#install_docs clojure-grimoire doc-cljs-core    org.clojure clojurescript
#install_docs clojure-grimoire doc-core-async   org.clojure core.async
install_docs clojure-grimoire doc-core-typed   org.clojure core.typed
install_docs clojure-grimoire doc-core-typed   org.clojure core.typed.rt
install_docs clojure-grimoire doc-lib-grimoire org.clojure-grimoire lib-grimoire
