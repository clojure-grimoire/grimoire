#!/bin/bash

HASH=$(shasum -a 256 project.clj | awk '{print $1}')

if [ ! -f ".$HASH" ]
then
    echo "[dbg] Generating classpath file!"
    lein with-profile server classpath > ".$HASH"
fi

CP=$(cat ".$HASH")

exec java -cp "$CP" clojure.main -m grimoire.web.service
