#!/bin/bash

HASH=$(shasum -a 256 project.clj | awk '{print $1}')
CPFILE="target/.$HASH"

make clean setup

if [ ! -f "$CPFILE" ]
then
    echo "[dbg] Generating classpath file!"
    lein with-profile server classpath > "$CPFILE"
fi

CP=$(cat "$CPFILE")

exec java -cp "$CP" clojure.main -m grimoire.web.service
