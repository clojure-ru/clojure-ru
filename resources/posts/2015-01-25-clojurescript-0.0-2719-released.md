---
title: Вышел clojurescript-0.0-2719
tags: release clojurescript
description: Простое подключение зависимостей!
---

### Сторонние библиотеки

Но ClojureScript не планировал быть изолированным от экосистемы JavaScript, так же как и Clojure от JVM платформы, стараясь использовать все ее преемущества. Но до последнего времени эта цель жертвовалась в угоду достижения оптимизации при компиляции. Google Closure Compiler позволяет делать очень глубокую оптимизацию кода (в частности, отсекая неиспользуемый код библиотек и тем самым значительно сокращая объем кода, подлежащего минификации и передаче на клиент). Однако, это создавало трудности при подключении библиотек, несовместимых с ним. На мимуточку, среди них jQuery, React, D3 и другие. Тем, кто не застал "темные времена", можно почитать [о способах подключения сторонних зависимостей](http://lukevanderhart.com/2011/09/30/using-javascript-and-clojurescript.html) из-за требовательного Google Closure Compiler и [об использовании jQuery](https://github.com/ibdknox/jayq), в частности.

### Все, хватит сложностей

0.0-2719 полностью поддерживает подключение любых библиотек, используя опробованные годами: `deps.cljs` и опцию `:foreign-libs`.

`deps.cljs` - это EDN файл-манифест в корне `jar`-файла с дополнительной информацией для компилятора. Например, для react.jar пакета он выглядит так:

	{:foreign-libs [{:file     "react/react.js"
	                 :file-min "react/react.min.js"
	                 :provides ["com.facebook.React"]}
	                {:file     "react/react_with_addons.js"
	                 :file-min "react/react_with_addons.min.js"
	                 :provides ["com.facebook.ReactWithAddons"]}]
	 :externs ["react/externs/react.js"]}

В этом файле все, что нужно для всех способов компиляции и REPL'а.

Пример использования:

	(ns foo.bar
		(:require com.facebook.React))

	(enable-console-print!)

	(println
		(. js/React
			(renderToString
				(. js/React (DOM.div nil "Hello!")))))
		 
В REPL:

	cljs.user> (require 'com.facebook.React)
	cljs.user> (. js/React 
	            (renderToString 
	              (. js/React (DOM.div nil "Hello!"))))
	
Теперь *не надо*:			  

- подключать `<script>` в режиме разработки
- располагать эти скрипты в нужном порядке
- добавлять React в `:preamble` при `:advanced` компиляции (которое форсировало его появление в начале всех скомпилированных файлов)
- явно указывать `:externs`

Однако, теперь все библиотеки для ClojureScript должны быть запакованы в `jar`, который содержит такой файл.

### Почему не Bower?

Некоторые вполне обоснованно спросят, почему бы не использовать проверенные решения для управления js-зависимостями, например, Bower?

- Во-первых, Bower требует nodejs-платформу, тогда как ClojureScript рассматривает NodeJs как опциональную, одну из платформ.
- Bower создан для управления зависимостями, однако в Clojure экосистеме этой цели уже служит Maven, а теперь он будет делать то же для ClojureScript.
- Наконец, Bower не решает проблему динамической подгрузки библиотек, и это непросто, так как JavaScript библиотеки используют разные решения (CommonJS, AMD), а некоторые вообще не оформлены как модуль, заставляя пользователей по-старинке подключать их как `<script>` на страницу. Текущее решение устраняет "парадокс выбора".
	
### Что теперь?

Теперь, когда мы приступили к снятию сильнейшей боли от разработки, остается запаковать самые популярные JavaScript библиотеки, которые устраняют пробелы в ClojureScript экосистеме.

Надо собраться и сделать. Необходима обратная связь. Это серьезное изменение и команда разработчиков стремится отполировать изменения как можно скорее.

### Что еще поменялось?

- [CLJS-985](http://dev.clojure.org/jira/browse/CLJS-985): ex-info теперь не теряет стектрейс
- [CLJS-984](http://dev.clojure.org/jira/browse/CLJS-984): REPL использует публичное API NodeJs
- [CLJS-963](http://dev.clojure.org/jira/browse/CLJS-963): не геренируется goog/dep.js при уровне оптимизации :none

### Исправленные баги

- [CLJS-982](http://dev.clojure.org/jira/browse/CLJS-982): Разыменование Var с учетом семантики Clojure
- [CLJS-980](http://dev.clojure.org/jira/browse/CLJS-980): Трейс перекрывал приглашение REPL
- [CLJS-979](http://dev.clojure.org/jira/browse/CLJS-979): ClojureScript REPL needs error handling for the special functions
- [CLJS-971](http://dev.clojure.org/jira/browse/CLJS-971): :reload работает с require-macros
- [CLJS-979](http://dev.clojure.org/jira/browse/CLJS-979): ClojureScript REPL обрабатывает ошибки специальных функций
- [CLJS-936](http://dev.clojure.org/jira/browse/CLJS-936): принятие >2 агрументов в битовую операцию
- [CLJS-962](http://dev.clojure.org/jira/browse/CLJS-962): исправлено непостоянное хеширование пустых коллекций
