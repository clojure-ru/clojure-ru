---
title: repl
---

~~~ clojure
;; Read-Eval-Print-Loop
;; как у динамических языков
user=> (+ 1 2 3)
6

;; его можно использовать для
;; интерактивной работы с JVM
user=> (import java.util.Date)
java.util.Date
user=> (->> Date .getMethods (take 1))
(#<Method public boolean java.util.Date.equals(java.lang.Object)>)
~~~
