cheatsheet:
	bash cheatsheet.sh

dat:
	bash dat.sh

build: cheatsheet dat

test: build
	lein serve

deploy:
	git push deployment master
