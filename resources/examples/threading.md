---
title: threading
---

~~~ clojure
;; префиксная нотация непривычна
user=> (filter even? (range 5))
(0 2 4)

;; но ее можно записать естественнее
;; threading макросами: -> / ->>
user=> (->> 5 range (filter even?))
(0 2 4)
~~~
