---
title: Мутирующие структуры данных
sidebar: Документация
---

*Если дерево падает в лесу, издает ли оно звук?  
Если чистая функция изменяет некоторые локальные данные, чтобы получить неизменяемое возвращаемое значение, это нормально?*

Это интересный вопрос. Структуры данных Clojure используют мутацию каждый раз, когда вы вызываете, например, **assoc**, создавая один или несколько массивов и мутируя их, а затем возвращая их для последующего неизменяемого использования. Причина заключается в производительности - вы просто не сможете работать так же быстро, используя только чистые функции и неизменяемые данные. Однако после создания и совместного использования неизменяемые и постоянные данные необходимы для надежной работы программ. То, что Clojure изменяет внутри себя - это небольшие, вновь выделенные массивы, которые представляют собой внутренние узлы его структур данных. Никто никогда не видит эти массивы.

Вы столкнетесь с подобным сценарием на более высоком уровне, когда захотите инициализировать или преобразовать большую постоянную структуру данных с помощью нескольких шагов, ни один из которых не будет виден никакому коду, кроме кода конструирования/преобразования. Сложность здесь заключается в том, что источником преобразования будет существующая постоянная структура данных, а результат функции _будет_ общим. Копирование в традиционную изменяемую структуру данных и обратно требует `O(n)` копирования, а внутренний код представляет собой императивный беспорядок, совершенно не похожий на остальной код Clojure. Кроме того, нет никаких средств защиты от случайного совместного использования или псевдонимов изменяемой структуры данных, особенно если для выполнения этой работы необходимо вызывать вспомогательные функции. Короче говоря, было бы жаль, если бы вам пришлось покинуть модель Clojure для того, чтобы ускорить такой кусок кода, как этот. Мутирующие структуры данных - это решение этой проблемы оптимизации, которое интегрируется в модель Clojure и обеспечивает те же гарантии безопасности потоков, которые вы ожидаете от Clojure.

## Как они работают

Мутирующие структуры данных всегда создаются на основе существующей постоянной структуры данных Clojure. Начиная с версии Clojure 1.1.0, поддерживаются векторы, отображения. Обратите внимание, что не все структуры данных Clojure могут поддерживать эту возможность, но большинство из них будут. Списки не поддерживаются, так как в этом нет никакой пользы.

Вы можете получить мутирующую "копию" структуры данных, вызвав команду **transient**. При этом создается новая мутирующая структура данных, которая является копией исходной и имеет те же характеристики производительности. Фактически, она в основном _является_ исходной структурой данных, и это подчеркивает первую особенность Мутирующих структур - создание такой структуры является `O(1)`. Он разделяет структуру со своим источником, так же как постоянные копии разделяют структуру.

Вторая особенность мутирующих данных заключается в том, что создание мутирующих данных не изменяет источник, и источник не может быть изменен через использование мутирующих данных. Ваши исходные данные неизменны и постоянны, как всегда.

Мутирующие элементы поддерживают интерфейс источника только для чтения, то есть вы можете вызывать **nth**, **get**, **count** и `fn-call` мутирующего вектора, как и постоянного вектора.

Мутирующие векторы _**не**_ поддерживают постоянный интерфейс исходной структуры данных. **assoc**, **conj** и т.д. будут вызывать исключения, потому что мутирующие векторы не являются постоянными. Таким образом, вы не можете случайно пропустить мутирующий объект в контекст, требующий постоянного.

Мутирующие процессы поддерживают параллельный набор "изменяющих" операций, с похожими именами, за которыми следует **!** - **assoc!**, **conj!** и т.д. Они выполняют те же действия, что и их постоянные аналоги, за исключением того, что возвращаемые значения сами являются мутирующими. Обратите особое внимание на то, что мутирующие значения не предназначены для обработки на месте. Вы должны перехватить и использовать возвращаемое значение в следующем вызове. Таким образом, они поддерживают ту же структуру кода, что и функциональный постоянный код, который они заменяют. Как будет показано в примере, это позволит вам легко повысить производительность части кода без структурных изменений.

Когда вы закончите сборку результатов, вы можете создать постоянную структуру данных, вызвав **persistent!** на мутирующем элементе. Эта операция также является `O(1)`. После вызова **persistent!** мутирующий объект не должен использоваться, и все операции будут вызывать исключения. Это будет справедливо и для любых псевдонимов, которые вы могли создать.

Вот очень типичный пример, код, который строит вектор для возврата, причем все "изменения" локальны для функции. Обратите внимание, что версия, использующая мутации, имеет точно такую же структуру, просто:

* Вызов **transient** на исходном векторе.
* Использование **conj!** вместо **conj**
* Вызов **persistent!** при возврате

```
(defn vrange [n]
  (loop [i 0 v []]
    (if (< i n)
      (recur (inc i) (conj v i))
      v)))

(defn vrange2 [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))

;; benchmarked (Java 1.8, Clojure 1.7)
(def v (vrange 1000000))    ;; 73.7 ms
(def v2 (vrange2 1000000))  ;; 19.7 ms
```

Да, _**транзиенты - это быстро!**_

## Одновременное использование

Это все, что касается использования мутирующих процессов, но у них есть еще одно важное ограничение: **мутирующие процессы требуют изоляции потоков.** Поскольку каждый результат мутирующие операции имеет общую (мутабельную) структуру с предыдущим, будет ошибкой, если более чем один поток будет манипулировать мутирующим процессом одновременно. Использование конкретного мутирующего экземпляра должно контролироваться либо использованием его в однопоточной области видимости, либо фреймворком, обеспечивающим это.

В Clojure 1.6 и более ранних версиях мутирующие элементы обнаруживали любое использование (чтение или запись) из потока, отличного от того, который их создал, и выдавали исключение. Эта проверка была удалена в 1.7, чтобы обеспечить более гибкое использование в таких фреймворках, как `go` блоках библиотеки `core.async`, которые обеспечивают однопоточное ограничение другими средствами.

## Итого

Мутирующие процессы обеспечивают высокопроизводительную оптимизацию функционального кода построения структур данных, который работает со структурами данных Clojure и обеспечивает критические гарантии безопасности.

* Однопутевое использование
* `O(1)` создание из постоянных структур данных
* Совместное использование структуры с персистентным источником
* `O(1)` создание персистентной структуры данных после завершения сеанса редактирования
* Та же структура кода, что и в функциональной версии
  * Захват возвращаемого значения, использование для следующего вызова
  * Не персистентные, поэтому вы не можете использовать промежуточные значения или псевдонимы.
* Невозможно использовать после возврата постоянной версии.
* Быстры

Мутирующие постоянные векторы, хэш-отображения и хэш-множества были добавлены в Clojure 1.1.
