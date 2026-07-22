---
title: Расширение доступа
description: Изучите, как использовать расширители доступа из файлов в формате classtweaker.
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

Расширение доступа — это тип [модификации классов](../class-tweakers), используемый для ослабления ограничений доступа к классам, методам и полям и отражения этих изменений в декомпилированном исходном коде.
Это включает в себя перевод их в публичные, расширяемые и/или изменяемые.

Элементы access widener могут быть [транзитивными](../class-tweakers/index#transitive-entries), чтобы изменения были видны модам, зависящим от вашего мода.

Для доступа к полям или методам зачастую безопаснее и проще использовать [accessor mixins](../mixins/accessors),
однако есть два случая, когда их недостаточно и требуется расширение доступа:

- Если вам нужен доступ к приватному или защищённому классу, или к классу с видимостью по умолчанию
- Если вам нужно переопределить финальный метод или расширить финальный класс

Однако, в отличие от [accessor mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors), [модификация классов](../class-tweakers) работает только с классами Vanilla Minecraft и не применяется к другим модам.

## Директивы доступа {#access-directives}

Элементы access widener начинаются с одного из трёх ключевых слов-директив, которые определяют тип применяемого изменения.

### Accessible (Доступный) {#accessible}

`accessible` может быть применено к классам, методам и полям:

- Поля и классы становятся публичными.
- Методы тоже становятся публичными, а также финальными, если изначально были приватными.

Расширение видимости метода или поля также расширяет видимость его класса.

### Extendable (Расширяемый) {#extendable}

`extendable` может быть применено к классам и методам:

- Классы становятся публичными и нефинальными
- Методы становатся защищёнными и нефинальными

Изменение расширяемости метода также меняет расширяемость его класса.

### Mutable (Изменяемый) {#mutable}

`mutable` может сделать поле изменяемым.

Чтобы сделать приватное неизменяемое поле доступным и изменяемым, вам нужно создать два отдельных включения в файле.

## Указание целей {#specifying-targets}

Для модификации классов используются их [внутренние имена](../mixins/bytecode#class-names). Для полей и методов вам нужно указать название их класса, их название и их [дескриптор байткода](../mixins/bytecode#field-and-method-descriptors).

::: tabs

== Классы

Формат:

```classtweaker:no-line-numbers
<accessible / extendable>    class    <className>
```

Пример:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#accesswidening_examples_classes

== Методы

Формат:

```classtweaker:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Пример:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#accesswidening_examples_methods

== Поля

Формат:

```classtweaker:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

Пример:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#accesswidening_examples_fields

:::

## Генерация записей {#generating-entries}

Ручное написание записей access widener занимает много времени и подвержено человеческим ошибкам. Давайте рассмотрим инструменты, которые упрощают часть процесса, позволяя генерировать и копировать записи.

### mcsrc.dev {#mcsrc-dev}

Доступный для всех версий с [необфусцированным JAR](../migrating-mappings/index#whats-going-on-with-mappings), а именно 1.21.11 и выше,
[mcsrc](https://mcsrc.dev) позволяет декомпилировать и просматривать исходный код Minecraft в браузере, а также копировать цели для Mixin, access widener или access transformer в буфер обмена.

Чтобы скопировать запись access widener, сначала перейдите к классу, который вы хотите изменить, и ПКМ по нужному элементу, чтобы открыть контекстное меню.

![ПКМ по цели в mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

Затем нажмите `Копировать Class Tweaker / Access Widener`, после чего в верхней части страницы появится подтверждение.

![Подтверждение копирования AW в mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

Затем вы можете вставить эту запись в файл class tweaker.

### Плагин Minecraft Development (IntelliJ IDEA) {#mcdev-plugin}

Плагин [Minecraft Development](../getting-started/intellij-idea/setting-up#installing-idea-plugins), также известный как MCDev, — это плагин для IntelliJ IDEA, который помогает в различных аспектах разработки модов для Minecraft.
Например, он позволяет копировать записи access widener из декомпилированного исходного кода в буфер обмена.

Чтобы скопировать запись access widener, сначала перейдите к классу, который вы хотите изменить, и ПКМ по нужному элементу, чтобы открыть контекстное меню.

![ПКМ по цели с MCDev](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

Затем нажмите `Копировать / Спец. вставка`, а затем `AW Entry`.

![Копировать/Спец. вставка в MCDev](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

Теперь над элементом, по которому вы щёлкнули ПКМ, должно появиться подтверждение.

![Подтверждение копирования AW в MCDev](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

Затем вы можете вставить эту запись в файл class tweaker.

## Применение изменений {#applying-changes}

Чтобы увидеть применённые изменения, необходимо обновить Gradle-проект и [пересгенерировать исходники](../getting-started/generating-sources). Целевые элементы должны
иметь соответствующим образом изменённые ограничения доступа. Если модификации не появляются, вы можете попробовать [валидацию файла](../class-tweakers/index#validating-the-file)
и проверить на наличие ошибок.
