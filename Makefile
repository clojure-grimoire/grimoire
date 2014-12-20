cheatsheet:
	bash cheatsheet.sh

dat:
	bash dat.sh

build: cheatsheet dat

test: build
	lein serve

deploy:
	git push deployment master

clean:
	rm -rf dat doc-store notes-store
