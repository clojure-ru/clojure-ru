---
title: Изобретение ООП на Clojure
tags: clojure, oop
description: каша из топора
---

[Оригинал на английском](https://nvbn.github.io/2015/01/30/reinventing-oop-with-clojure/).

Из книг все мы знаем, что среди главных принципов ООП находятся полиморфизм, инкапсуляция, но
другим, не менее важным аспектом ООП является передача сообщений. В Clojure у нас есть замечательная
библиотека &ndash; [core.async](https://github.com/clojure/core.async), которая имеет дело с сообщениями.
Так что мы можем сконструировать с ее помощью простой "объект" и использовать [core.match](https://github.com/clojure/core.match)
для "распознавания" сообщений. Да, это будет нечто похожее на акторы в Erlang:

```clojure
(require '[clojure.core.async :refer [go go-loop chan <! >! >!! <!!]])
(require '[clojure.core.match :refer [match]])

(def dog
  (let [messages (chan)]
    (go-loop []
      (match (<! messages)
        [:bark!] (println "Bark! Bark!")
        [:say! x] (println "Dog said:" x))
      (recur))
    messages))
```

Здесь я только что создал канал и в `go-loop` сравнивал сообщения с указанными образцами.

Форматом сообщений является `[:name & args]`.

Мы можем легко протестировать `dog` &ndash; объект, положив сообщение в канал.

```clojure
user=> (>!! dog [:bark!])
# Bark! Bark!

user=> (>!! dog [:say! "Hello world!"])

# Dog said: Hello world!
```

Посмотрите, как круто, но возможно, нам надо добавить состояние? Это просто:

```clojure
(def stateful-dog
  (let [calls (chan)]
    (go-loop [state {:barked 0}]
      (recur (match (<! calls)
               [:bark!] (do (println "Bark! Bark!")
                            (update-in state [:barked]
                                       inc))
               [:how-many-barks?] (do (println (:barked state))
                                      state))))
    calls))
```

Я только что задал начальное состояние для `go-loop` и
внутри `recur` заменял его новым состоянием после обработки сообщений.
Вот как мы момем это протестировать:

```clojure
user=> (>!! stateful-dog [:bark!])
# Bark! Bark!

user=> (>!! stateful-dog [:how-many-barks?])
# 1

user=> (>!! stateful-dog [:bark!])
# Bark! Bark!

user=> (>!! stateful-dog [:bark!])
# Bark! Bark!

user=> (>!! stateful-dog [:how-many-barks?])
# 3
```

Замечательно, но что если мы захотим получить результат метода? Это тоже просто:

```clojure
(def answering-dog
  (let [calls (chan)]
    (go-loop [state {:barked 0}]
      (recur (match (<! calls)
               [:bark! _] (do (println "Bark! Bark!")
                              (update-in state [:barked]
                                         inc))
               [:how-many-barks? result] (do (>! result (:barked state))
                                             state))))
    calls))
```

Я только что принимаю канал во второй части сообщения и отдаю результат в него.
Это уже не просто использовать как в предыдущих примерах, но возможо:

```clojure
user=> (>!! answering-dog [:bark!  (chan)])
# Bark! Bark!

user=> (>!! answering-dog [:bark!  (chan)])
# Bark! Bark!

user=> (let [result (chan)]
  #_=>   (>!! answering-dog [:how-many-barks? result])
  #_=>   (<!! result))
2
```

Последний вызов выглядит слишком сложным, давайте напишем вспомогательных функций,
чтобы сделать это проще:

```clojure
(defn call
  [obj & msg]
  (go (let [result (chan)]
        (>! obj (conj (vec msg) result))
        (<! result))))

(defn call!!
  [obj & msg]
  (<!! (apply call obj msg)))
```

`call!!` должен быть использован только вне `go-block`, `call` &mdash; в комбинации с `<!` и `<!!`.
Посмотрим:

```clojure
user=> (call!! answering-dog :how-many-barks?)
2

user=> (<!! (call answering-dog :how-many-barks?))
2

user=> (call!! answering-dog :set-barks!)
# Exception in thread "async-dispatch-33" java.lang.IllegalArgumentException: No matching clause: [:set-barks!...

user=> (call!! answering-dog :how-many-barks?)
# ...
```

Хьюстон, у нас проблема: когда ошибки происходят в объекте &ndash; объект "умирает" и больше не "отправляет" ответных сообщений.
Так что мы добавим `try/except` ко всем методам. Для автоматизации этого лучше использовать макрос. Но перед этим мы должны определить
формат ответа:

* `[:ok val]` &ndash; все круто;
* `[:error error-reason]` &ndash; произошла ошибка;
* `[:none]` &ndash; мы не можем отправить `nil` в канал, поэтому мы делаем так.

Ага, вы, чуваки, знаете, что это похоже на монаду Maybe/Option.

Теперь напишем макросы:

```clojure
(defn ok! [ch val] (go (>! ch [:ok val])))

(defn error! [ch reason] (go (>! ch [:error reason])))

(defn none! [ch] (go (>! ch [:none])))

(defmacro object
  [default-state & body]
  (let [flat-body (mapcat macroexpand body)]
    `(let [calls# (chan)]
       (go-loop ~default-state
         (recur (match (<! calls#)
                  ~@flat-body
                  [& msg#] (do (error! (last msg#) [:method-not-found (first msg#)])
                               ~@(take-nth 2 default-state)))))
       calls#)))

(defmacro method
  [pattern & body]
  [pattern `(try (do ~@body)
                 (catch Exception e#
                   (error! ~(last pattern) e#)))])
```

Макрос `object` может быть использован для создания объекта, а макрос `method` &mdash; 
для определения методов внутри объекта. Вы могли заметить, что `[& msg#]`
работает точно также, как и `method_missing` в Ruby.

Теперь мы можем создавать объекты, используя эти макросы:

```clojure
(defn make-cat
  [name]
  (object [state {:age 10
                  :name name}]
    (method [:get-name result]
      (ok! result (:name state))
      state)
    (method [:set-name! new-name result]
      (none! result)
      (assoc state :name new-name))
    (method [:make-older! result]
      (error! result :not-implemented)
      state)))

(def cat (make-cat "Simon"))
```

Мы создали объект `cat` с методами `get-name`, `set-name!`, `make-older!`, а `make-cat` - это импровизированный конструктор.
Этот объект может быть использован подобно всем предыдущим объектам, а в комбинации с `core.match` оно будет еще поолезней:

```clojure
user=> (match (call!! cat :get-name)
  #_=>   [:ok val] (println val))
# Simon

user=> (match (call!! cat :set-name! "UltraSimon")
  #_=>   [:none] (println "Name changed"))
# Name changed

user=> (match (call!! cat :get-name)
  #_=>   [:ok val] (println val))
# UltraSimon

user=> (match (call!! cat :make-older!)
  #_=>   [:ok age] (println "Now - " age)
  #_=>   [:error reason] (println "Failed with " reason))
# Failed with  :not-implemented

user=> (match (call!! cat :i-don't-know-what)
  #_=>   [:error _] (println "Failed"))
# Failed
```

Выглядит потрясающе! Но это не все, позднее я покажу как сделать наследование на этой основе.

