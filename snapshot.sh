#!/bin/bash
lein serve 4000 &
PID=$!
sleep 10

wget -r -p -nH -P resources/static/ http://127.0.0.1:4000/
zip -r resources/public/static.zip resources/static
rm -r resources/static

kill $PID
