---
title: Встановлення Java у Windows
description: Покрокова інструкція щодо встановлення Java на Windows.
authors:
  - IMB11
---

Інструкція по тому як встановити Java 21 у Windows.

Інструкція по тому як встановити Java 21 у Windows.

## 1. Перевірте, чи Java уже встановлено {#1-check-if-java-is-already-installed}

Щоб перевірити, чи Java вже встановлено, потрібно спочатку відкрити командний рядок.

Щоб перевірити, чи Java вже встановлено, потрібно спочатку відкрити командний рядок.

Ви можете зробити це, натиснувши <kbd>Win</kbd> <kbd>R</kbd> і ввівши `cmd.exe` у поле, що з’явиться.

![Діалогове вікно запуску Windows із "cmd.exe" на панелі запуску](/assets/players/installing-java/windows-run-dialog.png)

Якщо команда виконана успішно, ви побачите щось подібне. Якщо команда не вдалася, перейдіть до наступного кроку.

![Командний рядок із введеним "java -version"](/assets/players/installing-java/windows-java-version.png)

:::warning
Щоб використовувати Minecraft 1.21, вам знадобиться встановити принаймні Java 21. Якщо ця команда показує будь-яку версію, нижчу за 21, вам потрібно буде оновити встановлювач Java.
:::

## 2. Завантажити завантажувач Java 21 {#2-download-the-java-installer}

Щоб установити Java 21, вам потрібно буде завантажити програму встановлення з Adoptium.

Щоб установити Java 21, вам потрібно буде завантажити програму встановлення з Adoptium.

Ви захочете завантажити версію `Windows Installer (.msi)`:

![Сторінка завантаження Adoptium із виділеним встановлювачем Windows (.msi)](/assets/players/installing-java/windows-download-java.png)

Більшість сучасних комп’ютерів матиме 64-розрядну операційну систему. Якщо ви не впевнені, спробуйте скористатися 64-розрядним завантаженням.

## 3. Запустіть встановлювач! {#3-run-the-installer}

Виконайте кроки встановлювача, щоб встановити Java 21. Коли ви перейдете на цю сторінку, ви повинні встановити такі функції на «Уся функція буде встановлена ​​на локальному жорсткому диску»:

- `Установити змінну середовища JAVA_HOME` - її буде додано до вашого ШЛЯХУ.
- `JavaSoft (Oracle) registry keys`

![Встановлювач Java 21 із виділеними параметрами «Встановити змінну JAVA_HOME» і «ключами реєстру JavaSoft (Oracle)»](/assets/players/installing-java/windows-wizard-screenshot.png)

![Встановлювач Java 21 із виділеними параметрами «Встановити змінну JAVA\_HOME» і «ключами реєстру JavaSoft (Oracle)»](/assets/players/installing-java/windows-wizard-screenshot.png)

## 4. Перевірте, чи встановлено Java 21 {#4-verify-that-java-is-installed}

Після завершення встановлення ви можете переконатися, що Java 21 встановлено, знову відкривши командний рядок і ввівши `java -version`.

Після завершення встановлення ви можете переконатися, що Java 21 встановлено, знову відкривши командний рядок і ввівши `java -version`.

![Командний рядок із введеним "java -version"](/assets/players/installing-java/windows-java-version.png)

![Командний рядок із введеним "java -version"](/assets/players/installing-java/windows-java-version.png)
