---
title: Установка Java на Windows
description: Пошаговое руководство по установке Java в Windows.
authors:
  - IMB11
  - skycatminepokie
next: false
---

Это руководство поможет вам установить Java 21 на Windows.

Это руководство понадобится вам, если вы хотите использовать установщик на основе `.jar` Fabric'а, или если вы используете `.jar` сервера Minecraft.

## 1. Проверьте наличие уже установленной Java {#1-check-if-java-is-already-installed}

Чтобы проверить, что Java уже установлена, вам нужно сначала открыть командную строку.

Для этого нажмите <kbd>Win</kbd>+<kbd>R</kbd> и введите `cmd.exe` в появившемся окне.

![Окно "Выполнить" с введённым "cmd.exe"](/assets/players/installing-java/windows-run-dialog.png)

После того как вы открыли командную строку, впишите `java -version` и нажмите <kbd>Enter</kbd>.

Если команда выполнена успешно, вы увидите что-то вроде этого. Если выдало ошибку, то переходите к следующему шагу.

![Командная строка с введённой командой "java -version"](/assets/players/installing-java/windows-java-version.png)

::: warning

Чтобы использовать Minecraft 1.21.10, вам понадобится как минимум Java 21.

Если эта команда отображает версию ниже 21, вам необходимо обновить существующую установку Java; продолжайте читать эту страницу.

:::

## 2. Загрузите установщик Java 21 {#2-download-the-java-installer}

Чтобы установить Java 21, вам необходимо загрузить установщик с сайта [Adoptium](https://adoptium.net/temurin/releases?version=21&os=windows&arch=any&mode=filter).

Вам нужно скачать версию `Windows Installer (.msi)`:

![Adoptium c выделенным Windows Installer (.msi)](/assets/players/installing-java/windows-download-java.png)

Вам нужно выбрать `x86`, если у вас 32-битная система, или `x64`, если у вас 64-битная операционная система.

Большинство современных компьютеров имеют 64-битную операционную систему. Если вы не уверены, используйте 64-битный установщик.

## 3. Запустите установщик! {#3-run-the-installer}

Следуйте указаниям программы установки, чтобы установить Java 21. Когда вы дойдёте до этой страницы, вы должны выбрать "Entire feature will be installed on local hard drive" для следующих функций:

- `Set JAVA_HOME environment variable` - будет добавлено в PATH.
- `JavaSoft (Oracle) registry keys`

![Установщик Java 21 c выделенными "Set JAVA_HOME variable" и "JavaSoft (Oracle) registry keys"](/assets/players/installing-java/windows-wizard-screenshot.png)

После того как вы закончите это, вы можете нажать `Next` и продолжить установку.

::: warning

Windows не всегда сообщает другим программам о том, что Java установлен, до тех пор пока вы не перезагрузите компьютер.

**Убедитесь что вы перезапустили свой компьютер перед тем как продолжить!**

:::

## 4. Убедитесь, что Java 21 установлена {#4-verify-that-java-is-installed}

После завершения установки, вы можете проверить что Java 21 установлена открытием командного запроса заново и ввести `java -version`.

Если команда будет выполнена успешно, вы увидите что-то вроде показанного ранее, где отображается версия Java:

![Командная строка с введённой командой "java -version"](/assets/players/installing-java/windows-java-version.png)

Если у вас возникнут проблемы, не стесняйтесь обращаться за помощью в [Fabric Discord](https://discord.fabricmc.net/) в канале `#player-support`.
