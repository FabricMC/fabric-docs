---
title: Внедрение в интерфейс
description: Узнайте, как реализовывать интерфейсы в классах Minecraft в декомпилированных исходниках.
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - CelDaemon
  - Earthcomputer
  - its-miroma
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

Вставка интерфейсов — это вид [модификации классов](../class-tweakers/), используемый для добавления реализаций интерфейсов к классам Minecraft
в декомпилированном исходном коде.

Отображение реализации в декомпилированном исходном коде класса устраняет необходимость приведения к интерфейсу для использования его методов.

Кроме того, внедрение интерфейсов может быть [транзитивным](../class-tweakers/index#transitive-entries), что позволяет библиотекам легче
предоставлять свои добавленные методы модам, которые от них зависят.

Чтобы продемонстрировать внедрение интерфейсов, в примерах кода на этой странице мы добавим новый вспомогательный метод в класс `FlowingFluid`.

## Создание интерфейса {#creating-the-interface}

В пакете, отличном от вашего пакета миксинов (mixin package), создайте интерфейс, который вы хотите внедрить:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface-injection-example-interface

В нашем случае мы настроим выброс исключения по умолчанию (throw by default), так как планируем реализовать этот метод через миксин.

::: warning

Все методы внедряемых интерфейсов обязательно должны быть типа `default`, чтобы их можно было внедрить с помощью модификатора классов — даже если вы планируете реализовать эти методы в целевом классе через миксин.

К названиям методов также следует добавлять префикс с ID вашего мода и разделителем (например, `$` или `_`), чтобы они не конфликтовали с методами, добавленными другими модами.

:::

## Реализация интерфейса {#implementing-the-interface}

::: tip

Если методы интерфейса полностью реализованы через его собственные default-методы, вам не нужно использовать миксин для внедрения интерфейса — будет достаточно одной записи в модификаторе классов.

:::

Чтобы переопределить методы интерфейса в целевом классе, вам следует использовать миксин, который реализует этот интерфейс и указывает в качестве цели класс, в который вы хотите его внедрить.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface-injection-example-mixin

Эти переопределения будут добавлены в целевой класс при выполнении программы, но они не появятся в декомпилированном исходном коде, даже если вы используете модификатор классов, чтобы сделать видимой саму реализацию интерфейса.

## Создание записи модификатора класса {#making-the-class-tweaker-entry}

Внедрение в интерфейс использует следующий синтаксис:

```classtweaker:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

При изменении классов, классы и интерфейсы используют свои [внутренние имена](../mixins/bytecode#class-names).

Для нашего примера интерфейса, запись будет выглядеть следующим образом:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-example-entry{classtweaker:no-line-numbers}

### Дженерик-интерфейсы {#generic-interfaces}

Если в вашем интерфейсе есть обобщения, вы можете указать их в записи. Для этого добавьте угловые скобки `<>`
в конце имени интерфейса, а между скобками укажите общие типы в формате сигнатуры байт-кода Java.

Формат сигнатуры:

| Тип данных                                         | Пример Java              | Синтаксис                                                                                 | Пример формата сигнатуры  |
| -------------------------------------------------- | ------------------------ | ----------------------------------------------------------------------------------------- | ------------------------- |
| Ссылочный тип (Класс)           | `java.lang.String`       | Формат [дескриптора](../mixins/bytecode#type-descriptors)                                 | `Ljava/lang/String;`      |
| Массив                                             | `java.lang.String[]`     | Формат [дескриптора](../mixins/bytecode#type-descriptors)                                 | `[Ljava/lang/String;`     |
| Примитивный тип                                    | `boolean`                | Символ [дескриптора](../mixins/bytecode#type-descriptors)                                 | `Z`                       |
| Параметр типа (Дженерик)        | `T`                      | `T` + имя + `;`                                                                           | `TT;`                     |
| Класс с дженериком                                 | `java.util.List<T>`      | L + [внутр. имя](../mixins/bytecode#class-names) + `<` + дженерики + `>;` | `Ljava/util/List<TT;>;`   |
| Универсальный символ (Wildcard) | `?`, `java.util.List<?>` | Символ `*`                                                                                | `*`, `java/util/List<*>;` |
| Ограничение сверху (extends)    | `? extends String`       | `+` + ограничение                                                                         | `+Ljava/lang/String;`     |
| Ограничение снизу (super)       | `? super String`         | `-` + ограничение                                                                         | `-Ljava/lang/String;`     |

Чтобы внедриться в данный интерфейс:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface-injection-generic-interface

дженериком `<? extends String, Boolean[]>`

Запись модификатора класса должна выглядеть вот так:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-generic-interface-entry{classtweaker:no-line-numbers}

## Применение изменений {#applying-changes}

Чтобы увидеть применённые изменения, необходимо обновить Gradle-проект и [перегенерировать исходники](../getting-started/generating-sources).
Если изменения не появились, попробуйте [проверить файл на валидность](../class-tweakers/index#validating-the-file) и посмотреть, не возникли ли ошибки.

Теперь добавленные методы можно использовать на экземплярах того класса, в который был внедрён интерфейс:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface-injection-using-added-method

При необходимости вы также можете переопределять эти методы в подклассах (наследниках) того класса, куда был внедрён интерфейс.
