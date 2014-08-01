#!/bin/bash
lein serve &
PID=$!
sleep 10

wget -r -p -P resources/static/ http://127.0.0.1:3000/
zip -r resources/public/static.zip resources/static
rm -r resources/static

kill $PID

