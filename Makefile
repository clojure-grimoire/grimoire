clean:
	rm -rf _site

build: clean versions
	jekyll build

versions:
	wget http://central.maven.org/maven2/org/clojure/clojure/1.6.0/clojure-1.6.0.jar
	java -jar clojure-1.6.0.jar doc.clj
	rm clojure-1.6.0.jar
	wget http://central.maven.org/maven2/org/clojure/clojure/1.5.1/clojure-1.5.1.jar
	java -jar clojure-1.5.1.jar doc.clj
	rm clojure-1.5.1.jar
	wget http://central.maven.org/maven2/org/clojure/clojure/1.5.0/clojure-1.5.0.jar
	java -jar clojure-1.5.0.jar doc.clj
	rm clojure-1.5.0.jar
	wget http://central.maven.org/maven2/org/clojure/clojure/1.4.0/clojure-1.4.0.jar
	java -jar clojure-1.4.0.jar doc-1.4.0.clj
	rm clojure-1.4.0.jar

deploy: build
	rsync -r --delete _site git@arrdem.com:/srv/www/grimoire.arrdem.com/
