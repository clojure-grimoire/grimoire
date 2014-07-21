cheatsheet:
	bash cheatsheet.sh

build: versions cheatsheet
	jekyll build

test: build
	jekyll serve --skip-initial

versions:
	lein with-profile 1.4 run namespaces-1.4.clj
	lein with-profile 1.5 run namespaces.clj
	lein with-profile 1.6 run namespaces.clj

deploy: build
	rsync -r --delete _site git@arrdem.com:/srv/www/grimoire.arrdem.com/
