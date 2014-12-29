java -jar ./static-app.jar --build
rsync -av --progress html/ clojure@razum2um.me:~/dev/
