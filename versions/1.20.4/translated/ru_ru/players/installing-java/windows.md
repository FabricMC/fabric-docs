---
title: Установка Java на Windows
description: Пошаговая инструкция по установке Java на Windows.
authors:
  - IMB11

search: false
---

# Установка Java на Windows

Этот гайд расскажет, как установить Java 17 на Windows.

Этот гайд понадобится вам, если вы хотите использовать установщик на основе `.jar` Fabric'а, или если вы используете `.jar` сервера Minecraft.

## 1. Проверьте, не установлена ли Java

Чтобы проверить, что Java уже установлена, вам нужно сначала открыть командную строку.

Вы можете сделать это, нажав <kbd>Win</kbd> + <kbd>R</kbd> и написав `cmd.exe` в появившемся окне.

![Окно "Выполнить" с введённым "cmd.exe"](/assets/players/installing-java/windows-run-dialog.png)

После того как вы открыли командную строку, впишите `java -version` и нажмите <kbd>Enter</kbd>.

Если команда выполнена успешно, вы увидите что-то вроде этого. Если выдало ошибку, то переходите к следующему шагу.

![Командная строка с введённой командой "java -version"](/assets/players/installing-java/windows-java-version.png)

:::warning
Чтобы использовать большинство современных версий Minecraft, вам потребуется установить как минимум Java 17. Если эта команда отображает любую версию ниже 17, вам придётся обновить Java.
:::

## 2. Скачивание установщика Java 17

Чтобы установить Java 17, вам нужно скачать установщик с [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows&package=jdk&version=17).

Вам нужно скачать версию `Windows Installer (.msi)`:

![Adoptium c выделенным Windows Installer (.msi)](/assets/players/installing-java/windows-download-java.png)

Вам нужно выбрать `x86`, если у вас 32-битная система, или `x64`, если у вас 64-битная операционная система.

Большинство современных компьютеров имеют 64-битную операционную систему. Если вы не уверены, используйте 64-битный установщик.

## 3. Запустите установщик!

Следуйте инструкциям в установщике, чтобы установить Java 17. Когда вы доходите до этой страницы, вы должны выбрать "Entire feature will be installed on local hard drive" для следующих функций:

- `Set JAVA_HOME environment variable` - будет добавлено в PATH.
- `JavaSoft (Oracle) registry keys`

![Установщик Java 17 c выделенными "Set JAVA_HOME variable" и "JavaSoft (Oracle) registry keys"](/assets/players/installing-java/windows-wizard-screenshot.png)

После этого нажмите на `Next` и продолжите установку.

## 4. Убедитесь, что Java 17 установлена

После того как установка закончится, вы можете проверить, что Java 17 установлена, открыв командную строку и вписав `java -version`.

Если команда будет выполнена успешно, вы увидите что-то вроде показанного ранее, где отображается версия Java:

![Командная строка с введённой командой "java -version"](/assets/players/installing-java/windows-java-version.png)

---

Если вы столкнулись с какими-либо проблемами, попросите помощи в [дискорде Fabric](https://discord.gg/v6v4pMv) в канале `#player-support`.
