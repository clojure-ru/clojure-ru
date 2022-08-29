---
title: Пространства имен
sidebar: Документация
---

Пространства имен являются связками между простыми (без указания пространства имен) символами и переменными и/или классами. Var-ы могут быть встроены в пространство имен, используя `def` или любой из его вариантов, в этом случае они имеют простой символ для имени и ссылку на содержащее их пространство имен, а пространство имен отображает этот символ на тот же var. Пространство имен также может содержать отображения от символов к var-ам, встроенным в другие пространства имен, используя [refer](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/refer) или [use](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/use), или от символов к объектам Class, используя [import](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/import). Обратите внимание, что пространства имен являются объектами первого класса, они могут быть перечислены и т.д. Пространства имен также динамичны, их можно создавать, удалять и изменять во время выполнения, в REPL и т.д.

Лучший способ установить новое пространство имен в верхней части исходного файла Clojure - это использовать макрос [ns macro](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns). По умолчанию это создаст новое пространство имен, которое будет содержать отображения для имен классов в java.lang плюс `clojure.lang.Compiler`, и функций в `clojure.core`.

В Repl лучше всего использовать [in-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/in-ns), в этом случае новое пространство имен будет содержать отображения только для имен классов в java.lang. Для доступа к именам из пространства имен `clojure.core` необходимо выполнить `(clojure.core/refer 'clojure.core)`. Пространство имен `user` в REPL уже сделало это.

Текущее пространство имен, _\*ns\*_, может и должно быть установлено только вызовом [in-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/in-ns) или [ns macro](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns), оба из которых создают пространство имен, если оно не существует.

## Связанные функции

Создание и переход в пространство имен: [in-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/in-ns) [ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns) [create-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/create-ns)  
Добавление в пространство имен: [alias](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/alias) [def](special_forms#def) [import](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/import) [intern](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/intern) [refer](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/refer)  
Поиск существующих пространств имен: [all-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/all-ns) [find-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/find-ns)  
Изучение пространства имен: [ns-name](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-name) [ns-aliases](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-aliases) [ns-imports](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-imports) [ns-interns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-interns) [ns-map](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-map) [ns-publics](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-publics) [ns-refers](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-refers)  
Получение пространства имен из символа: [resolve](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/resolve) [ns-resolve](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-resolve) [namespace](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/namespace)  
Удаление элементов: [ns-unalias](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-unalias) [ns-unmap](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/ns-unmap) [remove-ns](https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/remove-ns)