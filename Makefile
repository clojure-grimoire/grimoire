cheatsheet:
	bash cheatsheet.sh

versions:
	lein with-profile 1.4 run namespaces-1.4
	lein with-profile 1.5 run namespaces
	lein with-profile 1.6 run namespaces

snapshot: versions cheatsheet
	bash snapshot.sh

build: versions cheatsheet snapshot

test: build
	lein serve
