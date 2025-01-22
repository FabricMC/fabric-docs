---
title: Создание пользовательских частиц
description: Узнайте, как создать пользовательскую частицу с помощью Fabric API.
authors:
  - Superkat32
---

# Создание пользовательских частиц {#creating-custom-particles}

Частицы — мощный инструмент. Они могут добавить атомосферность красивой сцене или добавить напряжения в битву с боссом. Давайте добавим еще один!

## Регистрация пользовательской частицы {#register-a-custom-particle}

Мы добавим новую блестящую частицу, которая будет имитировать движение конечной стержневой частицы.

Сначала нам нужно зарегистрировать `ParticleType` в вашем классе [инициализатор мода](../../getting-started/project-structure#entrypoints), используя идентификатор вашего мода.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

«sparkle_particle» строчными буквами — это путь JSON к текстуре частицы. Позже вы создадите новый JSON-файл с таким же именем.

## Регистрация на стороне клиента {#client-side-registration}

После того, как вы зарегистрировали частицу в инициализаторе мода, вам также необходимо зарегистрировать частицу в инициализаторе на стороне клиента.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

В этом примере мы регистрируем нашу частицу на стороне клиента. Затем мы придаем частице некоторое движение, используя фабрику частиц конечного стержня. Это означает, что наша частица будет двигаться точно так же, как частица конечного стержня.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- Горячая клавиша IntelliJ: Ctrl+Alt+B
- Горячая клавиша Visual Studio Code: Ctrl+F12
  :::

## Создание JSON-файла и добавление текстур {#creating-a-json-file-and-adding-textures}

Вам нужно будет создать 2 папки в папке `resources/assets/mod-id/`.

| Путь к папке         | Обьяснение                                                                              |
| -------------------- | --------------------------------------------------------------------------------------- |
| `/textures/particle` | Папка `particle` будет содержать все текстуры для всех ваших частиц.    |
| `/particles`         | Папка `particles` будет содержать все json-файлы для всех ваших частиц. |

В этом примере у нас будет только одна текстура в `textures/particle` с названием "sparkle_particle_texture.png".

Затем создайте новый JSON-файл в `particles` с тем же именем, что и путь JSON, который вы использовали при регистрации ParticleType. Для этого примера нам потребуется создать `sparkle_particle.json`. Этот файл важен, поскольку он сообщает Minecraft, какие текстуры должна использовать наша частица.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
Вы можете добавить больше текстур в массив `textures`, чтобы создать анимацию частиц. Частица будет циклически проходить по текстурам в массиве, начиная с первой текстуры.
:::

## Тестирование новой частицы {#testing-the-new-particle}

После того как вы завершите работу над файлом JSON и сохраните ее, вы можете загрузить Minecraft и все протестировать!

Проверить, все ли работает, можно, введя следующую команду:

```mcfunction
/particle fabric-docs-reference:sparkle_particle ~ ~1 ~
```

![Демонстрация частицы](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
При выполнении этой команды частица появится внутри игрока. Чтобы увидеть его, вам, скорее всего, придется идти задом наперед.
:::

В качестве альтернативы вы также можете использовать командный блок, чтобы вызвать частицу с помощью той же команды.
