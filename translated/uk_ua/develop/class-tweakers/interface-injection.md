---
title: Упровадження інтерфейсу
description: Дізнайтеся, як реалізувати інтерфейси в класах Minecraft у декомпільованому джерелі.
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - Earthcomputer
  - its-miroma
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

Упровадження інтерфейсу — це тип [твікінгу класу](../class-tweakers/), який використовується для додавання реалізацій інтерфейсу до класів Minecraft у декомпільованому джерелі.

Реалізація, яка є видимою в декомпільованому вихідному коді класу, усуває необхідність приведення до інтерфейсу для використання його методів.

Крім того, упровадження інтерфейсу можуть бути [прохідними](../class-tweakers/index#transitive-entries), що дозволяє бібліотекам легше надавати додані методи модам, які залежать від них.

Щоб продемонструвати впровадження інтерфейсу, фрагменти цієї сторінки використовуватимуть приклад, де ми додаємо новий допоміжний метод до `FlowingFluid`.

## Створення інтерфейсу {#creating-the-interface}

У пакеті, який не є вашим пакетом міксина, створіть інтерфейс, який ви хочете додати:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface-injection-example-interface

У нашому випадку ми викидаємо усталено, оскільки ми плануємо реалізувати метод через міксин.

::: warning

Усі методи впровадження інтерфейсів мають бути `default`, щоб їх можна було впровадити за допомогою засобу налаштування класу, навіть якщо ви плануєте реалізувати методи в цільовому класі за допомогою міксина.

Методи також повинні мати префікс вашого ID мода з роздільником, таким як `$` або `_`, щоб вони не конфліктували з доданими методами інших модів.

:::

## Реалізація інтерфейсу {#implementing-the-interface}

::: tip

Якщо методи інтерфейсу повністю реалізовано за допомогою інтерфейсу `default`, вам не потрібно використовувати міксин для впровадження інтерфейсу, [запису твікера класу](#making-the-class-tweaker-entry) буде достатньо.

:::

Щоб створити перевизначення методів інтерфейсу в цільовому класі, вам слід використовувати міксин, який реалізує інтерфейс і націлений на клас, у який ви хочете впровадити інтерфейс.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface-injection-example-mixin

Перевизначення буде додано до цільового класу під час виконання, але не буде видно в декомпільованому джерелі, навіть якщо ви використовуєте твікер класу, щоб зробити реалізацію інтерфейсу видимою.

## Зроблення запису твікера класу {#making-the-class-tweaker-entry}

Упровадження інтерфейсу використовує такий синтаксис:

```classtweaker:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

Для твікінгу класів, класи та інтерфейси використовують свої [внутрішні імена](../mixins/bytecode#class-names).

Для нашого прикладу інтерфейсу запис буде таким:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-example-entry

### Загальні інтерфейси {#generic-interfaces}

Якщо у вашому інтерфейсі є загальні, ви можете вказати їх у записі. Для цього додайте кутові дужки `<>` в кінці назви інтерфейсу з узагальненими у форматі підпису байт-коду Java між дужками.

Формат сигнатури такий:

| Опис                                      | Приклад Java             | Синтаксис                                                                    | Приклад формату сигнатури |
| ----------------------------------------- | ------------------------ | ---------------------------------------------------------------------------- | ------------------------- |
| Тип класу                                 | `java.lang.String`       | Формат [дескриптора](../mixins/bytecode#type-descriptors)                    | `Ljava/lang/String;`      |
| Тип масиву                                | `java.lang.String[]`     | Формат [дескриптора](../mixins/bytecode#type-descriptors)                    | `[Ljava/lang/String;`     |
| Примітив                                  | `boolean`                | Символ [дескриптора](../mixins/bytecode#type-descriptors)                    | `Z`                       |
| Тип змінної                               | `T`                      | `T` + назва + `;`                                                            | `TT;`                     |
| Загальний тип класу                       | `java.util.List<T>`      | L + [унутрішнє ім'я](../mixins/bytecode#class-names) + `<` + загальні + `>;` | `Ljava/util/List<TT;>;`   |
| Символ підставлення                       | `?`, `java.util.List<?>` | Символ `*`                                                                   | `*`, `java/util/List<*>;` |
| Обмеження символу підставлення розширення | `? extends String`       | `+` + обмеження                                                              | `+Ljava/lang/String;`     |
| Обмеження символу підставлення супер      | `? super String`         | `-` + обмеження                                                              | `-Ljava/lang/String;`     |

Отже, щоб додати інтерфейс:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface-injection-generic-interface

із загальним `<? extends String, Boolean[]>`

Запис твікера класу буде таким:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-generic-interface-entry

## Застосування змін {#applying-changes}

Щоб побачити внесені реалізації інтерфейсу, оновіть проєкт Gradle за допомогою [перегенерування джерел](../getting-started/generating-sources).
Якщо зміни не показано, ви можете спробувати [перевірити](../class-tweakers/index#validating-the-file) файл та перевірити на наявність помилок.

Додані методи тепер можна використовувати в екземплярах класу, в який було впроваджено інтерфейс:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface-injection-using-added-method

Ви також можете перевизначити методи в підкласах цільового впровадження інтерфейсу, якщо це необхідно.
