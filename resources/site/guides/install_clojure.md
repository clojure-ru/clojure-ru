---
title: Установите Clojure
sidebar: Первые шаги
---

## Инструкции для Mac OS

Необходимые условия: [brew](install_clojure#brew), [Java](install_clojure#java).

Это добавляет команды `clojure` и `clj` в вашу систему из [clojure/tools tap](https://github.com/clojure/homebrew-tools):

```
brew install clojure/tools/clojure
```

## Инструкции по Linux

Необходимые условия: [Java](install_clojure#java), `bash`, `curl`, `rlwrap`.

Для установки с помощью Linuxbrew (требуется [brew](install_clojure#brew)):

```
brew install clojure/tools/clojure
```

Для установки с помощью программы установки скриптов Linux:

1.  Убедитесь, что установлены следующие зависимости: `bash`, `curl`, `rlwrap` и `Java`.
2.  Используйте сценарий `linux-install` для загрузки и запуска установки, которая создаст исполняемые файлы `/usr/local/bin/clj`, `/usr/local/bin/clojure` и каталог `/usr/local/lib/clojure`:

```
curl -O https://download.clojure.org/install/linux-install-1.11.1.1237.sh
chmod +x linux-install-1.11.1.1237.sh
sudo ./linux-install-1.11.1.1237.sh
```

Для установки в пользовательское место (например, `/opt/infrastructure/clojure`) используйте опцию `--prefix`:

```
sudo ./linux-install-1.11.1.1237.sh --prefix /opt/infrastructure/clojure
```

Вы также можете расширить MANPATH в `/etc/man_db.conf`, чтобы включить страницы руководства:

```
MANPATH_MAP /opt/infrastructure/clojure/bin /opt/infrastructure/clojure/man
```

Скрипт `linux-install` может быть удален после установки.

## Posix инструкции

Теперь также доступна POSIX-версия программы установки linux. Эта программа установки должна работать на Linux, BSD и даже Mac (но будет конфликтовать с brew!).

Для установки с помощью POSIX скриптового инсталлятора:

1.  Убедитесь, что установлены следующие зависимости: `bash`, `curl`, `rlwrap` и `Java`.
2.  Используйте сценарий `posix-install` для загрузки и запуска установки, которая создаст исполняемые файлы `/usr/local/bin/clj`, `/usr/local/bin/clojure` и каталог `/usr/local/lib/clojure`:

```
curl -O https://download.clojure.org/install/posix-install-1.11.1.1237.sh
chmod +x posix-install-1.11.1.1237.sh
sudo ./posix-install-1.11.1.1237.sh
```

Для установки в пользовательское место (например, `/opt/infrastructure/clojure`) используйте опцию `--prefix`:

```
sudo ./posix-install-1.11.1.1237.sh --prefix /opt/infrastructure/clojure
```

Вы также можете расширить MANPATH в `/etc/man_db.conf`, чтобы включить страницы руководства:

```
MANPATH_MAP /opt/infrastructure/clojure/bin /opt/infrastructure/clojure/man
```

Сценарий `posix-install` может быть удален после установки.

## Инструкции по Windows

Необходимые условия: [Java](install_clojure#java)

Ранняя версия clj для Windows доступна по адресу [clj on Windows](https://github.com/clojure/tools.deps.alpha/wiki/clj-on-Windows). Пожалуйста, оставляйте отзывы на [Ask Clojure](https://ask.clojure.org) или в слаке Clojurians в #clj-on-windows.

## Подробности предварительной установки

### Brew (Mac или Linux)

`brew` - это менеджер пакетов для Mac OS X или Linux. Чтобы установить `brew`:

* Перейдите на [https://brew.sh/](https://brew.sh/) и следуйте инструкциям по установке для вашей ОС.

### Java

Для работы Clojure требуется Java. Clojure официально поддерживает выпуски Java LTS (в настоящее время Java 8, 11 и 17), но также старается обеспечить работу и промежуточных версий. Вы можете использовать любой дистрибутив Java, будь то коммерческий релиз от Oracle или версия с открытым исходным кодом, основанная на OpenJDK (например, Temurin). Инструменты Clojure требуют только, чтобы команда `java` находилась в `PATH` или чтобы была установлена переменная окружения `JAVA_HOME`.

Если у вас еще не установлена Java, мы рекомендуем установить Adoptium Temurin 17.

Для использования программы установки Adoptium Temurin:

* Перейдите на [https://adoptium.net/](https://adoptium.net/)
* Загрузите и запустите программу установки, соответствующую вашей платформе.
* Убедитесь, что `java` находится в системном PATH.

На Mac вы также можете установить Temurin с помощью brew:

* `brew tap homebrew/cask-versions` - добавление крана cask-versions в Homebrew
* `brew install --cask temurin17` - установить Temurin 17 (ранее AdoptOpenJDK).

Проверьте версию вашей Java, выполнив `java --version`. Если это не Temurin 17, то вам может понадобиться добавить `java` в ваш `PATH`:
## Дополнительная информация

Для других версий инструментов командной строки Clojure смотрите [changelog](/releases/tools) для истории версий и [Clojure tap](https://github.com/clojure/homebrew-tools) для информации об установке старых версий или новых пререлизов вместо них.


