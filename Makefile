cheatsheet:
	bash cheatsheet.sh

build: cheatsheet snapshot

test: build
	lein serve

deploy:
	git push deployment master
