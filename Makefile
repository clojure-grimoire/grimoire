cheatsheet:
	bash cheatsheet.sh

versions:
	lein with-profile 1.4 run namespaces-1.4
	lein with-profile 1.5 run namespaces
	lein with-profile 1.6 run namespaces

build: versions cheatsheet

test: build
	lein serve
