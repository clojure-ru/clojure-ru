---
title: macros
---

~~~ clojure
;; макросы делают код лаконичнее
(let [v (rand-nth [1 2 nil]
  (if v
    (do
      (println v)
      v)))

;; вместо этого можно написать
(when-let [v (rand-nth [1 2 nil])
  (println v)
  v]
~~~
