---
title: Назначения клавиш
description: Создание назначений клавиш и обработка их нажатий.
authors:
  - cassiancc
  - dicedpixels
resources:
  https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg: Стандартная раскладка США
---

Minecraft обрабатывает ввод пользователя с периферийных устройств, таких как клавиатура и мышь, с помощью назначений клавиш (key mappings).
Многие из этих назначений можно настроить через меню настроек.

С помощью Fabric API вы можете создавать свои собственные назначения клавиш и реагировать на них в своем моде.

Назначения клавиш существуют только на стороне клиента. Это означает, что регистрация и обработка нажатий должны выполняться на стороне клиента. Для этого вы можете использовать **клиентский инициализатор** (client initializer).

## Создание назначения клавиши {#creating-a-key-mapping}

Назначение клавиши состоит из двух частей: привязки к конкретной клавише и категории, к которой она относится.

Давайте начнем с создания категории. Категория определяет группу назначений клавиш, которые будут отображаться вместе в меню настроек.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#category

Затем мы можем создать само назначение клавиши. Мы будем использовать класс `KeyMappingHelper` из Fabric API, чтобы одновременно зарегистрировать наше назначение.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#key_mapping

::: info

Обратите внимание, что названия токенов клавиш (`GLFW.GLFW_KEY_*`) подразумевают [стандартную раскладку США](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg).

Это означает, что если вы используете раскладку AZERTY, нажатие на клавишу <kbd>A</kbd> вернет значение `GLFW.GLFW_KEY_Q`.

:::

Залипающие клавиши (sticky keys) также можно создать с помощью `KeyMappingHelper`, передав экземпляр `ToggleKeyMapping` вместо обычного `KeyMapping`.

После регистрации вы сможете найти свои назначения клавиш в меню: _Настройки_ > _Управление_ > _Назначение клавиш_ (Options > Controls > Key Binds).

![Категории и назначения клавиш без перевода](/assets/develop/key-mappings/untranslated.png)

## Переводы {#translations}

Вам нужно будет добавить переводы как для самого назначения клавиши, так заглавия категории.

Ключ перевода для названия категории имеет вид `key.category.<namespace>.<path>`. Ключом перевода для назначения клавиши будет тот, который вы указали при его создании.

Переводы можно добавить вручную или с помощью [генерации данных](./data-generation/translations).

```json
{
  "key.category.example-mod.custom_category": "Example Mod Custom Category",
  "key.example-mod.send_to_chat": "Send to Chat"
}
```

![Категории и назначения клавиш с переводом](/assets/develop/key-mappings/translated.png)

## Обработка нажатий клавиш {#reacting-to-key-mappings}

Теперь, когда у нас есть назначение клавиши, мы можем реагировать на его активацию с помощью события клиентского тика (client tick event).

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#client_tick_event

Этот код будет выводить сообщение «Key Pressed!» в игровой чат при каждом нажатии привязанной клавиши. Имейте в виду, что удержание клавиши будет циклически отправлять сообщение в чат. Вам может потребоваться реализовать проверки (guards), если эта логика должна срабатывать строго один раз за нажатие.

![Сообщение в чате](/assets/develop/key-mappings/key_mapping_pressed.png)
