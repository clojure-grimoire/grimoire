cheatsheet:
	bash cheatsheet.sh

snapshot: cheatsheet
	bash snapshot.sh

build: cheatsheet snapshot

test: build
	lein serve

deploy: snapshot
	git push deployment master
	scp resources/public/static.zip git@arrdem.com:/srv/www/grimoire.arrdem.com/resources/public/
