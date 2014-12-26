---
title: macros2
---

~~~ clojure
;; макросы также используются в ядре языка
user=> (clojure.repl/source or)
(defmacro or
  ([] nil)
  ([x] x)
  ([x & next]
    `(let [or# ~x]
       (if or# or# (or ~@next)))))
~~~
