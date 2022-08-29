---
title: Редакторы
sidebar: Первые шаги
---

Clojure - интерактивный язык, и вы получите наилучшие впечатления от Clojure, только используя редактор, который поддерживает оценку кода (через подключенный REPL) и структурное редактирование (манипулирование формами кода, как будто это данные, а не текст). К счастью, существует множество отличных редакторов Clojure, предоставляющих эти и многие другие возможности для программистов.

Обратите внимание, что многие из описанных ниже редакторов находятся в стадии активной разработки. Ссылки и ресурсы здесь могут устареть. Мы приветствуем [вопросы](https://ask.clojure.org) и [проблемы](https://github.com/clojure/clojure-site/issues)!

## Emacs - самый популярный, самый настраиваемый

[Emacs](https://www.gnu.org/software/emacs/) - один из старейших редакторов, который до сих пор активно используется. Как платформа, расширяемая в своем собственном варианте Лиспа, опции существуют практически для всего, что вы можете захотеть сделать. Как и следовало ожидать, большая расширяемость означает, что настройка этого инструмента по своему вкусу может быть работой сама по себе, и для некоторых Emacs - это скорее стиль жизни, чем редактор. Тем не менее, для разработчиков Clojure существуют дружественные стартовые точки, и вы сами выбираете скорость освоения экосистемы Emacs.

Основные режимы Emacs, о которых следует знать:
* [clojure-mode](https://github.com/clojure-emacs/clojure-mode), основной режим Emacs, обеспечивающий поддержку шрифтовой блокировки (подсветки синтаксиса), отступов, навигации и рефакторинга для Clojure(Script).
* [CIDER](https://cider.mx/) расширяет Emacs с поддержкой интерактивного программирования на Clojure через cider-mode, минорный режим Emacs, который дополняет clojure-mode для обеспечения интерактивной поддержки.
* [inf-clojure](https://github.com/clojure-emacs/inf-clojure) - обеспечивает базовое взаимодействие с подпроцессом Clojure (REPL), основанное на идеях популярного пакета inferior-lisp. 

Некоторые места, с которых можно начать:
* [GNU Emacs - скачать и установить](https://www.gnu.org/software/emacs/download.html) - официальная страница GNU Emacs.
* [Practicalli guide to Spacemacs](https://practical.li/spacemacs/install-spacemacs/) - конфигурация Emacs для Clojure, созданная сообществом - включает clojure-mode и CIDER.
* [Prelude](https://prelude.emacsredux.com/en/latest/) - конфигурация Emacs для начинающих пользователей Emacs, не специфичная для Clojure, но включающая поддержку Clojure (clojure-mode, CIDER)
* [Aquamacs](https://aquamacs.org/) - Aquamacs - это Emacs, разработанный для пользователей Mac native и достаточный для минималистичного окружения Clojure в паре с режимом inf-clojure.

## IntelliJ - Clojure с уклоном в Java

[IntelliJ IDEA](https://www.jetbrains.com/idea/) - одна из ведущих "современных" IDE с поддержкой широкого спектра языков и инструментов. IntelliJ сделал себе имя как сложный редактор Java, использующий платформу статического анализа для обеспечения отличной изучаемости и рефакторинга больших кодовых баз Java. Версия IntelliJ IDEA Community Edition для разработки JVM доступна для [бесплатного скачивания] (https://www.jetbrains.com/idea/download/#section=mac).

Для разработки Clojure в IntelliJ доступны два важных плагина:

* [Cursive](https://cursive-ide.com/) - полнофункциональная среда разработки на языке Clojure.
* [clojure-extras](https://plugins.jetbrains.com/plugin/18108-clojure-extras/) - интегрирует дополнительные возможности, такие как встроенные результаты оценки и линтинг через [clj-kondo](https://github.com/clj-kondo/clj-kondo).

Cursive хорошо подходит, если вы уже знакомы с Java или собираетесь работать со смешанными Clojure/Java проектами.

## VS Code - быстро развивающийся, дружелюбный для новичков

[Visual Studio Code](https://code.visualstudio.com/) быстро развивается и используется для различных языков, и Clojure не является исключением.

Среда [Calva](https://calva.io/) для интерактивной разработки Clojure использует сочетание статического и динамического анализа для обеспечения широкого спектра поддержки языка. Кроме того, Calva приложила дополнительные усилия для того, чтобы стать доступной для новичков благодаря своему [Getting Started](https://calva.io/get-started-with-clojure/) опыту, который представляет собой версию с нулевой установкой, запускаемую в браузере.

## Vim - высокоэффективное редактирование текста

[Vim](https://www.vim.org/) (или vi) - это редактор с долгой историей, известный своей способностью быстро и эффективно редактировать текст. Он стал де-факто заменой vi, оригинального визуального редактора Unix, на котором он был основан.

Его уникальный, мощный пользовательский интерфейс настолько популярен, что большинство других редакторов, упомянутых выше, включают vi-моды, воспроизводящие основные функции пользовательского интерфейса Vim.

[Neovim](https://neovim.io/) - это форк Vim. Несмотря на высокую степень совместимости, между этими двумя редакторами есть некоторые существенные различия.

И Vim, и Neovim хорошо расширяемы, и для них доступно огромное количество плагинов. Большинство, но не все, плагины будут работать с обоими редакторами.

Следующие плагины обеспечивают поддержку Clojure как для Vim, так и для Neovim:
* [Fireplace](https://github.com/tpope/vim-fireplace) - поддержка Clojure и интеграция REPL.
* [vim-iced](https://liquidz.github.io/vim-iced/) - вдохновение CIDER
* [vim-slamhound](https://github.com/guns/vim-slamhound) - автоматическое переписывание пространств имен.
    
Следующие плагины обеспечивают поддержку Clojure только в Neovim:
* [Conjure](https://github.com/Olical/conjure) - ориентирован на интерактивную разработку.
    
В дополнение к плагинам Clojure, стоит также расширить Vim для улучшения редактирования s-выражений. Приведенные ниже плагины можно использовать как в Vim, так и в Neovim:

* [vim-sexp](https://github.com/guns/vim-sexp) - точное редактирование S-выражений
* [vim-sexp-mappings-for-regular-people](https://github.com/tpope/vim-sexp-mappings-for-regular-people) - альтернативные привязки клавиш, которые некоторые пользователи могут предпочесть 
* [vim-surround](https://github.com/tpope/vim-surround) - облегчает обертывание выражений в скобки, кавычки и другие лексемы.
    
Также существует множество плагинов, которые обеспечивают цветовое выделение круглых скобок.