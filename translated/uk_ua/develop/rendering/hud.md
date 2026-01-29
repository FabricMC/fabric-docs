---
title: Промальовування у HUD
description: Узнайте як використовувати API Fabric Hud для промальовування у HUD.
authors:
  - IMB11
  - kevinthegreat1
---

Ми вже коротко торкалися рендера речей у HUD на сторінці [основних концепцій рендера](./basic-concepts) і [використання контексту малювання](./draw-context), тому на цій сторінці ми будемо дотримуватись API Hud і параметра `DeltaTracker`.

## `HudRenderCallback` {#hudrendercallback}

::: warning

Раніше Fabric надавав `HudRenderCallback` для рендера в HUD. Через зміни у рендері HUD ця подія стала надзвичайно обмеженою та не підтримується після Fabric API 0.116. Використання настійно не рекомендується.

:::

## `HudElementRegistry` {#hudelementregistry}

Fabric надає API Hud для рендера та накладення елементів на HUD.

Для початку нам потрібно зареєструвати слухача `HudLayerRegistrationCallback`, який реєструє ваші елементи. Кожен елемент є `HudElement`. Екземпляр `HudElement` — це зазвичай лямбда, яка приймає екземпляри `GuiGraphics` і `DeltaTracker` як параметри. Перегляньте `HudElementRegistry` та пов’язані Javadocs, щоб дізнатися більше про те, як використовувати API.

Контекст малювання можна використовувати для доступу до різноманітних утиліт рендера, які надає гра, і доступу до стека сирих матриць. Перегляньте сторінку [контексту малювання](./draw-context), щоб дізнатися більше про контекст малювання.

### Дельта-трекер {#delta-tracker}

Клас `DeltaTracker` дозволяє отримати поточне значення `gameTimeDeltaPartialTick`. `gameTimeDeltaPartialTick` — це «прогрес» між останнім і наступним ігровими тактами.

Наприклад, якщо ми припустимо сценарій 200 к/с, гра запускає новий такт приблизно кожні 10 кадрів. Кожен кадр, `gameTimeDeltaPartialTick`, представляє, наскільки ми знаходимося між останнім і наступним тактами. Понад 11 кадрів ви можете побачити:

| Кадр | `gameTimeDeltaPartialTick`      |
| :--: | ------------------------------- |
|  `1` | `1`: Новий такт |
|  `2` | `1/10 = 0.1`                    |
|  `3` | `2/10 = 0.2`                    |
|  `4` | `3/10 = 0.3`                    |
|  `5` | `4/10 = 0.4`                    |
|  `6` | `5/10 = 0.5`                    |
|  `7` | `6/10 = 0.6`                    |
|  `8` | `7/10 = 0.7`                    |
|  `9` | `8/10 = 0.8`                    |
| `10` | `9/10 = 0.9`                    |
| `11` | `1`: Новий такт |

Ви можете отримати `gameTimeDeltaPartialTick`, викликавши `deltaTracker.getGameTimeDeltaPartialTick(false)`, де логічним параметром є `ignoreFreeze`, що, по суті, дозволяє просто ігнорувати, коли гравці використовують команду `/tick freeze`.

На практиці ви повинні використовувати `gameTimeDeltaPartialTick` лише тоді, коли ваші анімації залежать від тактів Minecraft. Для анімації на основі часу використовуйте `Util.getMillis()`, який вимірює реальний час.

У цьому прикладі ми використаємо `Util.getMillis()` для лінійної інтерполяції кольору квадрата, який рендериться на HUD.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Зміна кольору з часом](/assets/develop/rendering/hud-rendering-deltatick.webp)

Чому б вам не спробувати використати `gameTimeDeltaPartialTick` і подивитися, що відбувається з анімацією, коли ви виконуєте команду `/tick freeze`? Ви повинні побачити зависання анімації, коли `gameTimeDeltaPartialTick` стане постійним (припустимо, що ви передали `false` як параметр для `DeltaTracker#getGameTimeDeltaPartialTick`)
