---
title: Clojure для студентов
---

В настоящее время большинство высших учебных учреждений испольльзуют Pascal в качестве языка для обучения программированию на разных курсах. Во многом это объясняется косностью программ и незаинтересованностью преподавателей другими языками. Встречаются такие, которые превозносят простоту паскаля как аргумент в его пользу. С другой стороны, понимаешь, что язык не развивается, не предлагает возможностей функционального программирования, требует специфической среды для разработки, большинство инструментов которой остались со времен msdos. А еще, наверное, понимаешь, что написать в резюме "Pascal" в 2015 году, как минимум, архаично.

## "У меня курс на паскале, что делать?"

<!--TODO: вынести таблицу --> 

Во-первых, проверь адекватность преподавателя. Если он молод - есть все шансы склонить его на свою сторону, он без труда поймет лабораторные работы - в конце концов это Lisp. Во-вторых, аргументируй свой выбор, и к тебе серьезно будут относиться. Вот тебе в помощь [список серьезных университетов](http://www.pl-enthusiast.net/2014/09/02/who-teaches-functional-programming/), в которых курсы построены на функциональных языках. 
	
## Что clojure может предложить

Естественно, первое, что приходит на ум - это экосистема наукоемких Java библиотек, которые можно использовать "как есть" или через обертки для самых различных целей - обработки "больших данных" (BigData): [cascalog](http://cascalog.org/), процессинга человеческого языка (NLP): [clojure-opennlp](https://github.com/dakrone/clojure-opennlp), [clj-vw](https://github.com/engagor/clj-vw); машинного обучения: [deeplearning4j](http://deeplearning4j.org/), компьютерного зрения и распознавания образов (Computer Vision): [vision](http://nakkaya.com/vision.html), [clj-tesseract](https://github.com/antoniogarrote/clj-tesseract); нейронных сетей: [k9](https://github.com/gigasquid/k9), [synaptic](https://github.com/japonophile/synaptic), машинного обучения (Machine Learning) [clj-ml](https://github.com/antoniogarrote/clj-ml)...

Кроме того, есть наукоемкие библиотеки, написанные на самом Clojure, для распределенной обработки данных в realtime: [Storm](http://storm.apache.org) и [Onyx](https://github.com/MichaelDrogalis/onyx); для статистики: [incanter](https://github.com/incanter/incanter), [statistiker](https://github.com/clojurewerkz/statistiker); символьных вычислений: [expresso](https://github.com/clojure-numerics/expresso); графов: [loom](https://github.com/aysylu/loom) и другие. Более подробно можно посмотреть [здесь](http://clojure-datascience.herokuapp.com/).

На Clojure пишут другие языки программирования (в основном, в учебных целях), например, [Lux](https://github.com/LuxLang/lux), или [mal](https://github.com/kanaka/mal), clojure-подобный lisp, написанный на самых разных языках, в том числе, на самом Clojure.

Clojure можно попробовать использовать в нише Matlab или Mathematica, используя Java библиотеки для обсчета и визуализации данных, в "блокнотном" виде. Например, существуют замечательные вещи, например, такие как [gorilla-repl](http://gorilla-repl.org) (посмотрите на [его вводное видео](http://vimeo.com/87118206)).

## Почему не Haskell?

Существует мнение, что в европейской и американской университетской культуре место языка de-facto занял Хаскель, и это отчасти так, благодаря университетам Беркли и Стэндфорду. Но это не отменяет того факта, что Clojure используется в продвинутой университетской среде. Например, [университет Хельсинки даже имеет специальный курс](http://mooc.cs.helsinki.fi/clojure), и есть опенсорсные проекты, на которые выделяются образовательные гранты, например, [hypower-org/watershed](https://github.com/hypower-org/watershed/blob/e7af7b0be24a8c3bd9d94bf5fb8784e35424cb47/README.md#this-work-was-funded-by-the-national-science-foundation-through-grant-cns-1239221) 