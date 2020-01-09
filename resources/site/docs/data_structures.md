---
title: Структуры данных
sidebar: Документация
---

Clojure имеет богатый набор структур данных. Они имеют ряд общих свойств:

* Они неизменяемы
* Они доступны для чтения
* Они поддерживают правильную семантику равенства значений в своей реализации equals
* Они обеспечивают хорошие хэш-значения
* Кроме того, коллекции:
  * с которыми можно взаимодействовать через интерфейсы.
  * поддерживают порядок
  * поддерживают манипулирование персистентными данными
  * поддерживают метаданные
  * реализуют `java.lang.Iterable`
  * реализуют неопциональную (только для чтения) часть `java.util.Collection` или `java.util.Map`

## nil

`nil` - это возможное значение любого типа данных в Clojure. `nil` имеет то же значение, что и `null` в Java. Условная система Clojure основана на `nil` и `false`, причем `nil` и `false` представляют собой значения логической ложности в условных тестах - все остальное является логической истиной. Кроме того, `nil` используется в качестве значения конца последовательности в протоколе последовательности.

## Числа

Clojure по умолчанию обеспечивает полную поддержку примитивных значений JVM, что позволяет использовать высокопроизводительный и идиоматический код Clojure для числовых приложений.

Clojure также поддерживает "boxed" типы чисел Java, производные от `java.lang.Number`, включая `BigInteger` и `BigDecimal`, а также свой собственный тип `Ratio`.

### Длинное целое

По умолчанию Clojure работает с натуральными числами как с экземплярами примитивного типа long в Java. Когда в результате операции с примитивным целым числом получается значение, которое слишком велико, чтобы вместить его в примитивное значение, возникает исключение `java.lang.ArithmeticException`. Clojure предоставляет набор альтернативных математических операторов с суффиксом апострофа: `+'`, `-'`, `\*'`, `inc'` и `dec'`. Эти операторы автоматически переходят в `BigInt` при переполнении, но они менее эффективны, чем обычные математические операторы.

### Рациональные числа

Представляет отношение между целыми числами. Деление целых чисел, которые не могут быть приведены к целому числу, дает отношение, т.е. 22/7 = 22/7, а не значение с плавающей точкой или усеченное значение.

### Конвертация числовых типов

`BigInt` и типы с плавающей точкой "заражаются" при выполнении операций. То есть, любая целочисленная операция с `BigInt` приведет к `BigInt`, а любая операция с `double` или `float` приведет к `double`.

### Большие числа

Числовые литералы для BigInt и BigDecimal задаются с помощью постфикса N и M соответственно.

|        *Пример выражения*        |      *Возвращаемое значение*     |
| -------------------------------- | -------------------------------- |
|           `(== 1 1.0 1M)`        |              `true`              |
|           `(/ 2 3)`              |              `2/3`               |
|           `(/ 2.0 3)`            |        `0.6666666666666666`      |
|`(map #(Math/abs %) (range -3 3))`|        `(3 2 1 0 1 2)`           |
|  `(class 36786883868216818816N)` |       `clojure.lang.BigInt`      |
|   `(class 3.14159265358M)`       |       `java.math.BigDecimal`     |

### Связанные функции

Вычисления: [+](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2B) [\-](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2D) [\*](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2A) [/](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2F) [inc](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/inc) [dec](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/dec) [quot](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/quot) [rem](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/rem) [min](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/min) [max](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/max)  
Вычисление с автоматическим продвижением: [+'](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2B%27) [\-'](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2D%27) [\*'](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%2A%27) [inc'](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/inc%27) [dec'](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/dec%27)  
Сравнение: [\==](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%3D%3D) [<](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%3C) [<=](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%3C%3D) [\>](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%3E) [\>=](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/%3E%3D) [zero?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/zero%3F) [pos?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/pos%3F) [neg?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/neg%3F)  
Побитовые операции: [bit-and](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bit-and) [bit-or](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bit-or) [bit-xor](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bit-xor) [bit-not](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bit-not) [bit-shift-right](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bit-shift-right) [bit-shift-left](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bit-shift-left)  
Соотношения: [числитель](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/numerator) [denominator](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/denominator)  
Коэрцитивы: [int](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/int) [bigdec](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bigdec) [bigint](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/bigint) [double](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/double) [float](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/float) [long](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/long) [num](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/num) [short](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/short)

## Строки

Строки Clojure - это строки Java. См. также [Печать](/docs/other_functions.html#printing).

```
user=> (map (fn [x] (.toUpperCase x)) (.split "Dasher Dancer Prancer" " ")))
("DASHER" "DANCER" "PRANCER")
```

### Связанные функции

[str](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/str) [string?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/string?) [pr-str](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/pr-str) [prn-str](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/prn-str) [print-str](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/print-str) [println-str](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/println-str) [with-out-str](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/with-out-str)

## Текстовые символы

Текстовые символы Clojure - это текстовые символы Java.

### Связанные функции

[char](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/char) [char-name-string](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/char-name-string) [char-escape-string](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/char-escape-string)

## Ключевые слова

Ключевые слова - это символические идентификаторы, которые эквивалентны сами по себе. Они обеспечивают очень быструю проверку равенства. Как и символы, они имеют имена и необязательные [пространства имен](/docs/namespaces.html), оба из которых являются строками. Ведущий символ ':' не является частью пространства имен или имени.

Ключевые слова реализуют функцию `IFn` для вызова с одним аргументом (для функции `map`) и со вторым необязательным аргументом (значением по умолчанию). Например, `(:mykey my-hash-map :none)` означает то же самое, что `(get my-hash-map :mykey :none)`. См. [get](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/get).

### Связанные функции

[keyword](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/keyword) [keyword?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/keyword?)

## Символы

Символы - это идентификаторы, которые обычно используются для обозначения чего-то другого. Они могут использоваться в формах программ для ссылки на параметры функций, привязки let, имена классов и глобальные переменные. У них есть имена и необязательные [пространства имен](/docs/namespaces.html), оба из которых являются строками. Символы могут иметь метаданные (см. [with-meta](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/with-meta)).

Символы, как и ключевые слова, реализуют `IFn` для функции invoke() одного аргумента (для функции `map`) с необязательным вторым аргументом (значение по умолчанию). Например, `('mysym my-hash-map :none)` означает то же самое, что `(get my-hash-map 'mysym :none)`. См. [get](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/get).

### Связанные функции

[symbol](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/symbol) [symbol?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/symbol?) [gensym](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/genysm) (см. также макрос #-suffix [reader](reader))

## Коллекции

Все коллекции Clojure являются неизменяемыми и [персистентными](https://en.wikipedia.org/wiki/Persistent_data_structure). В частности, коллекции Clojure поддерживают эффективное создание "модифицированных" версий, используя структурное разделение, и дают все свои гарантии производительности для постоянного использования. Коллекции эффективны и по своей сути потокобезопасны. Коллекции представлены абстракциями, и может существовать одна или несколько конкретных реализаций. В частности, поскольку операции "модификации" порождают новые коллекции, новая коллекция может не иметь того же конкретного типа, что и исходная коллекция, но будет иметь тот же логический (интерфейсный) тип.

Все коллекции поддерживают [count](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/count) для получения размера коллекции, [conj](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/conj) для "добавления" к коллекции и [seq](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/seq) для получения последовательности, которая может пройтись по всей коллекции, хотя их специфическое поведение немного отличается для разных типов коллекций.

Поскольку коллекции поддерживают функцию [seq](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/seq), все функции [sequence](последовательности) могут быть использованы с любой коллекцией.

### Хэши коллекций Java

Интерфейсы коллекций Java определяют алгоритмы для [Lists](https://docs.oracle.com/javase/8/docs/api/java/util/List.html#hashCode()), [Sets](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html#hashCode()) и [Maps](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#hashCode()) при вычислении значений `hashCode()`. Все коллекции Clojure соответствуют этим спецификациям в своих реализациях `hashCode()`.

### Хэши коллекций Clojure

Clojure предоставляет свои собственные вычисления хэшей, которые обеспечивают лучшие свойства хэшей для коллекций (и других типов), известные как значение _hasheq_.

Интерфейс `IHashEq` помечает коллекции, которые предоставляют функцию `hasheq()` для получения значения `hasheq`. В Clojure для вычисления значения hasheq можно использовать функцию [hash](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/hash).

Упорядоченные коллекции (вектор, список, последовательность и т.д.) должны использовать следующий алгоритм вычисления hasheq (где hash вычисляет hasheq). Обратите внимание, что для вычисления целочисленных переполнений используются алгоритмы `unchecked-add-int` и `unchecked-multiply-int`.

```
(defn hash-ordered [collection]
  (-> (reduce (fn [acc e] (unchecked-add-int
                            (unchecked-multiply-int 31 acc)
                            (hash e)))
              1
              collection)
      (mix-collection-hash (count collection))))
```

Неупорядоченные коллекции (отображения, множества) должны использовать следующий алгоритм для вычисления hasheq. Запись в отображении рассматривается как упорядоченная коллекция ключей и значений. Обратите внимание, что для вычисления целочисленных переполнений используется `unchecked-add-int`.

```
(defn hash-unordered [collection]
  (-> (reduce unchecked-add-int 0 (map hash collection))
      (mix-collection-hash (count collection))))
```

Алгоритм [mix-collection-hash](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/mix-collection-hash) является деталью реализации, которая может быть изменена.

## Списки

Списки - это коллекции. Они реализуют интерфейс `ISeq` напрямую. (Обратите внимание, что пустой список также реализует `ISeq`, однако функция `seq` всегда будет возвращать `nil` для пустой последовательности). [count](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/count) - O(1). [conj](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/conj) помещает элемент в начало списка.

### Связанные функции

Создать список: [list](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/list) [list\*](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/list*)  
Обрабатывать список как стек: [peek](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/peek) [pop](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/pop)  
Исследовать список: [list?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/list?)

## Векторы

Вектор - это коллекция значений, индексированных целыми числами. Векторы поддерживают доступ к элементам по индексу за `log32n` операций. [count](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/count) - O(1). [conj](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/conj) помещает элемент в конец вектора. Векторы также поддерживают [rseq](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/rseq), который возвращает элементы в обратном порядке. Векторы реализуют `IFn`, для вызова `invoke()`, который они принимают за индекс и ищут как будто с вызовом функции `nth`, т.е. векторы являются функциями своих индексов. Векторы сравниваются сначала по длине, затем каждый элемент сравнивается по порядку.

### Связанные функции

Создать вектор: [vector](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/vector) [vec](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/vec) [vector-of](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/vector-of)  
Исследовать вектор: [get](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/get) [nth](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/nth) [peek](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/peek) [rseq](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/rseq) [vector?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/vector?)  
'изменить' вектор: [assoc](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/assoc) [pop](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/pop) [subvec](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/subvec) [replace](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/replace)

См. также [zippers](other_libraries)

## Отображения (хеши)

Отображение - это коллекция, которая сопоставляет ключи со значениями. Предусмотрено два различных типа отображения - хэшированные и сортированные. Для хэш-отображений требуются ключи, которые корректно поддерживают `hashCode` и `equals`. Сортированные отображения требуют ключей, реализующих `Comparable` или экземпляр `Comparator`. Хэш-отображения обеспечивают более быстрый доступ (log32n операций) по сравнению с (logN операций), но отсортированные отображения - это просто отсортированные отображения. [count](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/count) - это O(1). [conj](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/conj) ожидает другое (возможно, однозаходное) отображение в качестве элемента и возвращает новое отображение, которое представляет собой старое отображение плюс записи из нового, которые могут перезаписывать записи старого. [conj](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/conj) также принимает `MapEntry` или вектор из двух элементов (ключ и значение). [seq](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/seq) возвращает последовательность записей отображения, которые представляют собой пары ключ/значение. Сортированное отображение также поддерживает [rseq](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/rseq), которая возвращает записи в обратном порядке. Отображения реализуют `IFn`, для `invoke()` одного аргумента (ключа) с необязательным вторым аргументом (значением по умолчанию), т.е. отображения являются функциями своих ключей. `nil` ключи и значения являются нормальными.

### Связанные функции

Создайте новое отображение: [hash-map](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/hash-map) [sorted-map](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/sorted-map) [sorted-map-by](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/sorted-map-by)  
'изменить' отображение: [assoc](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/assoc) [dissoc](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/dissoc) [select-keys](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/select-keys) [merge](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/merge) [merge-with](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/merge-with) [zipmap](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/zipmap)  
Изучите отображение: [get](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/get) [contains?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/contains?) [find](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/find) [keys](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/keys) [vals](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/vals) [map?](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/map?)  
Изучите запись отображения: [key](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/key) [val](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/val)

## Структуры

Для большинства случаев использования структур лучше использовать [records](datatypes).

Часто многие экземпляры отображений имеют одинаковый базовый набор ключей, например, когда отображения используются как структуры или объекты в других языках. `StructMaps` поддерживают этот случай использования, эффективно разделяя информацию о ключах, и одновременно предоставляя опциональные улучшенные аксессоры к этим ключам. Структурные отображения - это во всех отношениях отображения, поддерживающие тот же набор функций, совместимые со всеми другими отображениями и постоянно расширяемые (т.е. структурные отображения не ограничены своими базовыми ключами). Единственное ограничение заключается в том, что вы не можете отделить struct map от одного из ее базовых ключей. Отображение структур будет сохранять свои базовые ключи в порядке их следования.

Структурные отображения создаются путем создания сначала объекта основы структуры с помощью [create-struct](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/create-struct) или [defstruct](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/defstruct), затем создаются экземпляры с помощью [struct-map](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/struct-map) или [struct](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/struct).

```
(defstruct desilu :fred :ricky)
(def x (map (fn [n]
              (struct-map desilu
                :fred n
                :ricky 2
                :lucy 3
                :ethel 4))
             (range 100000)))
(def fred (accessor desilu :fred))
(reduce (fn [n y] (+ n (:fred y))) 0 x)
 -> 4999950000
(reduce (fn [n y] (+ n (fred y))) 0 x)
 -> 4999950000
```

### Связанные функции

Создание StructMap: [create-struct](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/create-struct) [defstruct](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/defstruct) [accessor](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/accessor)  
Создание отдельной структуры: [struct-map](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/struct-map) [struct](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/struct)

## ArrayMaps

При манипулировании формой кода часто желательно иметь отображение, которое сохраняет порядок ключей. Отображение массива является таким отображением - оно просто реализуется как массив key val key val... Как таковое, оно имеет линейную производительность поиска и подходит только для _очень маленьких_ отображений. Он реализует полный интерфейс отображения. Новые отображения массивов могут быть созданы с помощью функции [array-map](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/array-map). Обратите внимание, что отображение массива сохраняет порядок сортировки только в том случае, если оно не "модифицировано". Последующие объединения в конечном итоге приведут к тому, что оно превратится в хэш-отображение.

## Множества

Множества - это коллекции уникальных значений.

Существует буквальная поддержка хэш-множеств:

```
#{:a :b :c :d}
-> #{:d :a :b :c}
```

Вы можете создавать множества с помощью функций [hash-set](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/hash-set) и [sorted-set](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/sorted-set):

```
(hash-set :a :b :c :d)
-> #{:d :a :b :c}

(sorted-set :a :b :c :d)
-> #{:a :b :c :d}
```

Вы также можете получить набор значений в коллекции с помощью функции [set](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/set):

```
(set [1 2 3 2 1 2 3])
-> #{1 2 3}
```

Множества - это коллекции:

```
(def s #{:a :b :c :d})
(conj s :e)
-> #{:d :a :b :e :c}

(count s)
-> 4

(seq s)
-> (:d :a :b :c)

(= (conj s :e) #{:a :b :c :d :e})
-> true
```

Множества поддерживают 'удаление' с помощью [disj](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/disj), а также `contains?` и `get`, последний возвращает объект, содержащийся в множестве, который сравнивается с ключом, если он найден:

```
(disj s :d)
-> #{:a :b :c}

(contains? s :b)
-> true

(get s :a)
-> :a
```

Множества являются функциями своих членов, используя `get`:

```
(s :b)
-> :b

(s :k)
-> nil
```

Clojure предоставляет основные операции с множествами, такие как [union](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/union) / [difference](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/difference) / [set](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/intersection), а также некоторую поддержку псевдореляционной алгебры для "отношений", которые являются просто множествами отображений - [select](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/select) / [index](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/index) / [rename](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/rename) / [join](https://clojure.github.io/clojure/clojure.set-api.html#clojure.set/join).
