cheatsheet:
	bash cheatsheet.sh

build: cheatsheet

test: build
	lein serve

deploy:
	git push deployment master
