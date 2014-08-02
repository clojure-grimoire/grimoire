cheatsheet:
	bash cheatsheet.sh

versions:
	lein with-profile 1.4 run namespaces-1.4
	lein with-profile 1.5 run namespaces
	lein with-profile 1.6 run namespaces

snapshot: cheatsheet
	bash snapshot.sh

build: cheatsheet snapshot

test: build
	lein serve
