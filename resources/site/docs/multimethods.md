---
title: Мультиметоды и иерархии
sidebar: Документация
---

Clojure отказывается от традиционного объектно-ориентированного подхода создания нового типа данных для каждой новой ситуации, предпочитая создавать большую библиотеку функций на небольшом наборе типов. Тем не менее, Clojure полностью признает ценность полиморфизма во время выполнения для создания гибкой и расширяемой архитектуры системы. Clojure поддерживает сложный полиморфизм во время выполнения с помощью системы мультиметодов, которая поддерживает диспетчеризацию типов, значений, атрибутов и метаданных, а также отношений между одним или несколькими аргументами.

Мультиметод Clojure - это комбинация _диспетчеризирующей_ _функции_ и одного или нескольких _методов_. Когда мультиметод определяется с помощью `defmulti`, необходимо указать диспетчерскую функцию. Эта функция будет применена к аргументам мультиметода для получения _диспетчерского значения_. Затем мультиметод попытается найти метод, связанный с диспетчерским значением, или значение, из которого получено диспетчерское значение. Если такой метод был определен (через [defmethod](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/defmethod)), то он будет вызван с аргументами, и это будет значением вызова мультиметода. Если метод не связан с диспетчеризируемым значением, мультиметод будет искать метод, связанный с диспетчеризируемым значением по умолчанию (которое по умолчанию равно `:default`), и будет использовать его, если он есть. В противном случае вызов будет ошибкой.

Система мультиметодов использует API: [defmulti](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/defmulti) создает новые мультиметоды, [defmethod](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/defmethod) создает и устанавливает новый метод мультиметода, связанный с диспетчерским значением, [remove-method](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/remove-method) удаляет метод, связанный с диспетчерским значением, и [prefer-method](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/prefer-method) создает порядок между методами, если в противном случае они были бы неоднозначны.

Производность определяется комбинацией либо наследования Java (для значений классов), либо с помощью специальной иерархической системы Clojure. Система иерархии поддерживает отношения производности между именами (либо символами, либо ключевыми словами), а также отношения между классами и именами. Функция [derive](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/derive) создает эти отношения, а функция [isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa?) проверяет их существование. Обратите внимание, что [isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa?) - это не [instance?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/instance?).

С помощью функции `(derive child parent)` можно определить иерархические отношения. `child` и `parent` могут быть символами или ключевыми словами и должны быть определены в пространстве имен:

Обратите внимание на синтаксис парсера `::`, `::keywords` разрешают пространства имен.

```
::rect
-> :user/rect
```

[derive](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/derive) - это фундаментальный механизм установления отношений

```
(derive ::rect ::shape)
(derive ::square ::rect)
```

[parents](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/parents) / [ancestors](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ancestors) / [descendants](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/descendants) и [isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa%3F) позволяют вам запрашивать иерархию

```
(parents ::rect)
-> #{:user/shape}

(ancestors ::квадрат)
-> #{:user/rect :user/shape}

(descendants ::shape)
-> #{:user/rect :user/square}
```

`(= x y)` подразумевает `(isa? x y)`

```
(isa? 42 42)
-> true
```

`isa?` использует систему иерархии

```
(isa? ::square ::shape)
-> true
```

Вы также можете использовать класс в качестве дочернего элемента (но не родительского, единственный способ сделать что-то дочерним элементом класса - это наследование в Java).

Это позволяет накладывать новые таксономии на существующую иерархию классов Java:

```
(derive java.util.Map ::collection)
(derive java.util.Collection ::collection)

(isa? java.util.HashMap ::collection)
-> true
```

[isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa%3F) также проверяет отношения между классами:

```
(isa? String Object)
-> true
```

как и [parents](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/parents) / [ancestors](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ancestors) (но не [descendants](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/descendants), так как потомки класса являются открытым множеством)

```
(ancestors java.util.ArrayList)
-> #{java.lang.Cloneable java.lang.Object java.util.List
    java.util.Collection java.io.Serializable
    java.util.AbstractCollection
    java.util.RandomAccess java.util.AbstractList}
```

[isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa%3F) работает с векторами, вызывая [isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa%3F) на их соответствующих элементах:

```
(isa? [::square ::rect] [::shape ::shape])
-> true
```

## Диспетчерезация на основе isa?

Мультиметоды используют [isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa%3F), а не `=` при проверке на совпадение значений диспетчера. Обратите внимание, что первый тест [isa?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/isa%3F) равен `=`, поэтому точные совпадения работают.

```
(defmulti foo class)
(defmethod foo ::collection [c] :a-collection)
(defmethod foo String [s] :a-string)

(foo [])
:a-collection

(foo (java.util.HashMap.))
:a-collection

(foo "bar")
:a-string
```

[prefer-method](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/prefer-method) используется для однозначного определения в случае нескольких совпадений, когда ни одно из них не доминирует над другим. Вы можете просто объявить в каждом мультиметоде, что одно значение диспетчеризации предпочтительнее другого:

```
(derive ::rect ::shape)

(defmulti bar (fn [x y] [x y]))
(defmethod bar [::rect ::shape] [x y] :rect-shape)
(defmethod bar [::shape ::rect] [x y] :shape-rect)

(bar ::rect ::rect)
-> Execution error (IllegalArgumentException) в user/eval152 (REPL:1).
   Multiple methods in multimethod 'bar' match dispatch value:
   [:user/rect :user/rect] -> [:user/shape :user/rect]
   and [:user/rect :user/shape], and neither is preferred

(prefer-method bar [::rect ::shape] [::shape ::rect])
(bar ::rect ::rect)
-> :rect-shape
```

Все приведенные примеры используют глобальную иерархию, используемую системой multimethod, но целые независимые иерархии также могут быть созданы с помощью [make-hierarchy](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/make-hierarchy), и все приведенные выше функции могут принимать необязательную иерархию в качестве первого аргумента.

Эта простая система является чрезвычайно мощной. Один из способов понять связь между мультиметодами Clojure и традиционной однодиспетчерской обработкой в стиле Java заключается в том, что однодиспетчерская обработка похожа на мультиметод Clojure, чья диспетчерская функция вызывает `getClass` в качестве первого аргумента, и чьи методы связаны с этими классами. Мультиметоды Clojure не привязаны жестко к классу/типу, они могут основываться на любом атрибуте аргументов, на нескольких аргументах, могут выполнять проверку аргументов и передавать их методам обработки ошибок и т.д.

Примечание: В данном примере в качестве функции отправки используется ключевое слово `:Shape`, так как ключевые слова являются функциями отображений, как описано в разделе [Структуры данных](data_structures).

```
(defmulti area :Shape)
(defn rect [wd ht] {:Shape :Rect :wd wd :ht ht})
(defn circle [radius] {:Shape :Circle :radius radius radius})
(defmethod area :Rect [r]
    (* (:wd r) (:ht r)))
(defmethod area :Circle [c]
    (* (. Math PI) (* (:radius c) (:radius c))))
(defmethod area :default [x] :oops)
(def r (rect 4 13))
(def c (circle 12))
(area r)
-> 52
(area c)
-> 452.3893421169302
(area {})
-> :oops
```