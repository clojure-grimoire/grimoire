clean:
	rm -rf _site _includes/*.*.*/ *.*.*/ *.*.*.md *.jar

build: clean versions
	jekyll build

test: clean versions
	jekyll serve

versions:
	lein with-profile 1.4 run
	lein with-profile 1.5 run
	lein with-profile 1.6 run

deploy: build
	rsync -r --delete _site git@arrdem.com:/srv/www/grimoire.arrdem.com/
