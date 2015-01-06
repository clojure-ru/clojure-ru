#!/bin/bash

if [ ! -d static ]; then
  git submodule init
fi
if [ ! -f static/target/static-app.jar ]; then
  git submodule update
  (cd static; lein uberjar)
fi

java -jar static/target/static-app.jar --watch
rsync -av --progress html/ clojure@razum2um.me:~/dev/

