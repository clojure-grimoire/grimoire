build: versions
	jekyll build

test: versions
	jekyll serve

versions:
	lein with-profile 1.4 run
	lein with-profile 1.5 run
	lein with-profile 1.6 run

deploy: build
	rsync -r --delete _site git@arrdem.com:/srv/www/grimoire.arrdem.com/
