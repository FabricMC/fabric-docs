---
title: Створення власних частинок
description: Дізнайтеся, як створити власну частинку за допомогою Fabric API.
authors:
  - Superkat32
---

# Створення власних частинок {#creating-custom-particles}

Частинки — це потужний інструмент. Вони можуть додати атмосфери красивій сцені або додати напруги в битву з босом. Нумо додаймо одну!

## Реєстрація власних частинок {#register-a-custom-particle}

Ми додамо нову частинку блиску, яка імітуватиме рух частинки стрижня Енду.

Спочатку нам потрібно зареєструвати `ParticleType` у вашому [ініціалізаторі мода](../../getting-started/project-structure#entrypoints), класі за допомогою вашого ID моду.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

"sparkle_particle" малими літерами – це шлях JSON для текстури частинки. Пізніше ви створите новий файл JSON із такою назвою.

## Реєстрація на стороні клієнту {#client-side-registration}

Після того, як ви зареєстрували частинку в ініціалізаторі мода, вам також потрібно буде зареєструвати частинку в ініціалізаторі на стороні клієнта.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

В цьому прикладі ми реєструємо нашу частинку на стороні клієнта. Потім ми надаємо частинці деякий рух, використовуючи фабрику частинок стрижня Енду. Це означає, що наша частинка рухатиметься так само, як частинка стрижня Енду.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- Гаряча клавіша IntelliJ's: Ctrl+Alt+B
- Гаряча клавіша Visual Studio Code: Ctrl+F12
  :::

## Створення JSON і додання текстури {#creating-a-json-file-and-adding-textures}

Вам потрібно буде створити 2 теки у вашій теці `resources/assets/mod-id/`.

| Шлях до теки         | Пояснення                                                                          |
| -------------------- | ---------------------------------------------------------------------------------- |
| `/textures/particle` | Тека `particle` міститиме всі текстури для всіх ваших частинок.    |
| `/particles`         | Тека `particles` міститиме всі файли json для всіх ваших частинок. |

Для цього прикладу ми матимемо лише одну текстуру в `textures/particle` під назвою "sparkle_particle_texture.png".

Далі створіть новий файл JSON у `particles` з тим же ім’ям, що й шлях JSON, який ви використовували під час реєстрації свого ParticleType. Для цього прикладу нам потрібно буде створити `sparkle_particle.json`. Цей файл важливий, оскільки він дозволяє Minecraft знати, які текстури має використовувати наша частинка.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
Ви можете додати більше текстур до масиву `textures`, щоб створити анімацію частинок. Частинка буде циклічно переглядати текстури в масиві, починаючи з першої текстури.
:::

## Тестування нових частинок

Після того, як ви завершите файл JSON і збережете свою роботу, ви можете завантажити Minecraft і перевірити все!

Ви можете перевірити, чи все працює, ввівши таку команду:

```mcfunction
/particle fabric-docs-reference:sparkle_particle ~ ~1 ~
```

![Демонстрація частинки](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
За допомогою цієї команди частинка з’явиться всередині гравця. Ймовірно, вам доведеться пройти назад, щоб побачити це.
:::

Крім того, ви також можете використовувати командний блок, щоб викликати частинку за допомогою такої ж команди.
