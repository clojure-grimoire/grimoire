#!/usr/bin/env bash

set -e
mkdir -p dat
pushd dat

if [ ! -d "clojure-cheatsheets" ]; then
  git clone https://github.com/arrdem/clojure-cheatsheets
fi

pushd clojure-cheatsheets/
git reset --hard && git clean -fd && git pull origin master

cd src/clj-jvm
lein run links-to-grimoire
popd
popd
cp dat/clojure-cheatsheets/src/clj-jvm/cheatsheet-full.html .
sed  -i -e 1,3d cheatsheet-full.html
head -n -2 cheatsheet-full.html > _cheatsheet
mv _cheatsheet cheatsheet-full.html
perl -i -p -e 's%http://grimoire.arrdem.com%{{ site.baseurl }}%g' cheatsheet-full.html
perl -i -p -e 's%/1.6.0%\/{{ site.clojure_version }}%g' cheatsheet-full.html
perl -i -p -e 's%cheatsheet_files%{{ site.baseurl }}public%g' cheatsheet-full.html
mv -f cheatsheet-full.html resources/cheatsheet.html
