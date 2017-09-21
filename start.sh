#!/bin/bash

if [ ! -d static/target ]; then
  git submodule init
fi
if [ ! -f static/target/static-app.jar ]; then
  git submodule update
  (cd static; lein uberjar)
fi

java -jar static/target/static-app.jar --watch

