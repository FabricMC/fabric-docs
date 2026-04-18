---
title: Розширення доступу
description: Дізнайтеся, як використовувати розширювачі доступу з файлу твікера класу.
authors-nogithub:
  - lightningtow
  - siglong
authors:
  - Ayutac
  - cassiancc
  - CelDaemon
  - cootshk
  - Earthcomputer
  - florensie
  - froyo4u
  - haykam821
  - hYdos
  - its-miroma
  - kb-1000
  - kcrca
  - liach
  - lmvdz
  - matjojo
  - MildestToucan
  - modmuss50
  - octylFractal
  - OroArmor
  - T3sT3ro
  - Technici4n
  - TheGlitch76
  - UpcraftLP
  - YTG1234
---

Розширення доступу — це тип [твікінгу класу](../class-tweakers), який використовується для послаблення обмежень доступу до класів, методів і полів і відбиття цієї зміни в декомпільованому джерелі.
Це охоплює надання їм загальнодоступних, розширюваних і/або змінних.

Записи розширення доступу можуть бути [транзитивними](../class-tweakers/index#transitive-entries), щоб зробити зміни видимими для модів залежно від ваших.

Щоб отримати доступ до полів або методів, безпечніше та простіше використовувати [міксини доступу](https://wiki.fabricmc.net/tutorial:mixin_accessors), але є дві ситуації, коли засобів доступу недостатньо і необхідне розширення доступу:

- Якщо вам потрібен доступ до `private`, `protected` або пакетно-приватного класу
- Якщо вам потрібно перевизначити метод `final` або створити підклас `final` класу

Однак, на відміну від [міксинів доступу](https://wiki.fabricmc.net/tutorial:mixin_accessors), [твікінг класу](../class-tweakers) працює лише зі стандартними класами Minecraft, а не з іншими модами.

## Директиви доступу {#access-directives}

Записи розширювача доступу починаються з одного з трьох ключових слів директиви, які вказують тип модифікації, яку слід застосувати.

### Доступно {#accessible}

`accessible` може націлюватися на класи, методи та поля:

- Робить поля та класи публічними.
- Методи стають публічними та кінцевими, якщо спочатку були приватними.

Зроблення методу або поля доступним також робить доступним його клас.

### Розширювані {#extendable}

`extendable` може націлюватись на класи та методи:

- Класи стають публічними та некінцевими
- Методи стають захищеними та некінцевими

Зроблення методу розширюваним також робить його клас розширюваним.

### Змінний {#mutable}

`mutable` може зробити поле некінцевим.

Щоб зробити приватне і кінцеве поле одночасно доступним і змінним, ви повинні зробити два окремих записи у файлі.

## Визначення цілей {#specifying-targets}

Для твікінгу класів, класи використовують свої [внутрішні імена](../mixins/bytecode#class-names). Для полів і методів ви повинні вказати назву класу, ім’я та [дескриптор байт-коду](../mixins/bytecode#field-and-method-descriptors).

::: tabs

== Класи

Формат:

```:no-line-numbers
<accessible / extendable>    class    <className>
```

Приклад:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== Методи

Формат:

```:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Приклад:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== Поля

Формат:

```:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

Приклад:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

:::

## Генерація записів {#generating-entries}

Написання записів розширення доступу вручну займає багато часу та може призвести до людських помилок. Розгляньмо інструменти, які спрощують частину процесу, дозволяючи створювати та копіювати записи.

### mcsrc.dev {#mcsrc-dev}

Доступно для всіх версій із [необфускованим JAR](../migrating-mappings/index#whats-going-on-with-mappings), а саме 1.21.11 і вище, [mcsrc](https://mcsrc.dev) дозволяє декомпілювати та переміщатися з джерелом Minecraft у браузері та копіювати міксини, отримувати доступ до розширювача або переходу до цілей трансформатора в буфер обміну.

Щоб скопіювати запис розширювача доступу, спочатку перейдіть до класу, який ви хочете змінити, і натисніть ПКМ на вашій меті, щоб відкрити спливне меню.

![Натискання ПКМ на ціль у mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

Потім натисніть `Copy Class Tweaker / Access Widener`, і зверху сторінки має з’явитися підтвердження.

![Підтвердження копіювання розширювачів на mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

Потім ви можете вставити запис у свій файл твікер класу.

### Плаґін Development Minecraft (IntelliJ IDEA) {#mcdev-plugin}

[Плаґін Development Minecraft](../getting-started/intellij-idea/setting-up#installing-idea-plugins), також відомий як MCDev, — це плаґін IntelliJ IDEA, який допомагає в різних аспектах розробки модів Minecraft.
Наприклад, це дозволяє копіювати записи розширення доступу з декомпільованого джерела до буфера обміну.

Щоб скопіювати запис розширювача доступу, спочатку перейдіть до класу, який ви хочете змінити, і натисніть ПКМ на вашій меті, щоб відкрити спливне меню.

![Натискання ПКМ по цілі за допомогою MCDev](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

Потім натисніть `Copy / Paste Special` і `AW Entry`.

![Копіювання/вставлення особливостей за допомогою MCDev](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

Тепер на елементі, який ви натиснули ПКМ, має з’явитися підтвердження.

![Підтвердження копіювання розширювачів за допомогою MCDev](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

Потім ви можете вставити запис у свій файл твікер класу.

## Застосування змін {#applying-changes}

Щоб побачити внесені зміни, оновіть проєкт Gradle з [перегенеруванням джерел](../getting-started/generating-sources). Необхідно відповідним чином змінити обмеження доступу для елементів, які ви націлили. Якщо зміни не показано, ви можете спробувати [перевірити файл](../class-tweakers/index#validating-the-file) та перевірити на наявність помилок.
