---
title: Міграція мапінгів
description: Дізнайтеся, як мігрувати обфусковані мапінги вашого мода.
authors:
  - ArduFish
  - asiekierka
  - cassiancc
  - Daomephsta
  - deirn
  - Earthcomputer
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - liach
  - MildestToucan
  - modmuss50
  - natanfudge
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - basil4088
resources:
  https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition: Видалення обфускації Java Edition — Minecraft.net
  https://fabricmc.net/2025/10/31/obfuscation.html: Видалення обфускації з Fabric
  ../../404: Данина Fabric Yarn (2016-2025)
---

Якщо ви плануєте [оновити свій мод до 26.1 або вище](#whats-going-on-with-mappings), вам потрібно буде перейти з мапінгів Yarn на Mojang.

Є два способи зробити це: ви можете використовувати **плаґін Loom Gradle** або **плаґін Ravel IntelliJ IDEA**.

Loom пропонує напівавтоматичне переміщення мапінгів за допомогою завдання `migrateMappings`, але **не підтримує перенесення коду, написаного на Kotlin**.

Ravel — це плаґін для IntelliJ IDEA, який додає діалог інтерфейсу для міграції. Однак, на відміну від Loom, Ravel також **підтримує Kotlin**. Крім того, Ravel може працювати краще, ніж Loom, для складніших проєктів, оскільки він використовує IDE для вирішення змін.

::: info

Fabric API використовує Ravel для переходу з мапінгів Yarn на Mojang. Перегляньте [PR #4690 в репозиторії API Fabric](https://github.com/FabricMC/fabric/pull/4960).

:::

Жоден варіант не є ідеальним, і вам все одно доведеться переглядати результати та вносити виправлення вручну, особливо якщо ви переміщуєте міксини.

<ChoiceComponent :choices="[
{
 name: 'Loom Gradle Plugin',
 href: './loom',
 icon: 'simple-icons:gradle',
 color: '#4DC9C0',
},
{
 name: 'Ravel IntelliJ IDEA Plugin',
 href: './ravel',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
]" />

## Що відбувається з мапінгами? {#whats-going-on-with-mappings}

Історично Minecraft: Java Edition використовував обфускацію, що призвело до розробки мап обфускації, які Fabric Loom використовує для модифікації. Було два варіанти: або власні мапінги Yarn від Fabric, або офіційні мапінги Mojang.

Mojang нещодавно випустили [перший випуск Minecraft: Java Edition із необфускованим кодом](https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition), і Проєкт Fabric прийняв рішення [не підтримувати сторонні мапінги](https://fabricmc.net/2025/10/31/obfuscation.html) із цієї версії надалі. Якщо ви плануєте оновити свій мод до цієї версії, вам потрібно буде спочатку перейти до мапінгів Mojang перед оновленням.

## Що таке мапінги? {#mappings}

Minecraft: Java Edition був обфускований з моменту випуску до 1.21.11, що означає, що його код мав зручні для людини назви класів, як-от `Creeper`, замінені на тарабарщину, як-от `brc`. Щоб легко модифікувати його, Fabric Loom використовує мапи обфускації: посилання, які перетворюють заплутані назви класів, як-от `brc`, назад у зручні для людини назви, такі як `CreeperEntity`.

Як досвідчений розробник Fabric, ви зіткнетеся з трьома основними наборами назв:

- **Intermediary**: набір мапінгів, який використовується скомпільованими модами Fabric для обфускованих випусків; наприклад `brc` може стати `class_1548`. Суть Intermediary полягає в тому, що він пропонує стабільний набір імен у всіх випусках, оскільки обфусковані імена класів змінюються з кожною новою версією Minecraft. Це часто дозволяє модам, створеним для однієї версії, працювати на інших, якщо уражені частини гри не сильно змінилися.
- **Yarn**: набір мап із відкритим кодом, розроблений Fabric для написання модів людьми. Більшість модів Fabric використовували мапінгів Yarn, оскільки вони були типовими до відмови від них у 2025 році. Прикладом мапінгу може бути `CreeperEntity`.
- **Мапінги Mojang**: офіційні мапінги обфускації гри, випущені Mojang у 2019 році для допомоги в розробці модів. Примітно, що у мапінгах обфускації Mojang відсутні назви параметрів і Javadocs, тому деякі користувачі також накладають [Parchment](https://parchmentmc.org/) на офіційні мапінги. Прикладом мапінгу може бути `Creeper`.

Minecraft 26.1 деобфусковано і включає назви параметрів, тому немає потреби в будь-яких мапінгах обфускації.
