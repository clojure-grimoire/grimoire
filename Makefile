setup: clean
	bash cheatsheet.sh
	bash dat.sh
	echo "Done!"

test: setup
	lein serve

deploy:
	git push deployment master

clean:
	rm -rf doc-store notes-store
