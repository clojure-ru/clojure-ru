---
title: bytecode
---

~~~ clojure
;; на JVM язык компилируется в байт-код
user=> (use 'no.disassemble)
nil
user=> (-> (fn []) disassemble println)
public final class user$eval2155$fn__2156
  extends clojure.lang.AFunction {
  ...
  public user$eval2155$fn__2156();
    0  aload_0 [this]
    1  invokespecial clojure.lang.AFunction() [10]
    4  return
      Line numbers:
        [pc: 0, line: 1]
  ...
}
~~~
