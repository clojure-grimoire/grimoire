cheatsheet:
	bash cheatsheet.sh

snapshot: cheatsheet
	bash snapshot.sh

build: cheatsheet snapshot

test: build
	lein serve

versions:
	lein with-profile 1.4 run org.clojure clojure 1.4.0 namespaces-1.4
	lein with-profile 1.5 run org.clojure clojure 1.5.0 namespaces
	lein with-profile 1.6 run org.clojure clojure 1.6.0 namespaces

deploy: snapshot
	git push deployment master
	scp resources/public/static.zip git@arrdem.com:/srv/www/grimoire.arrdem.com/resources/public/
