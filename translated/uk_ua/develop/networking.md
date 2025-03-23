---
title: Мережа
description: Загальний посібник із роботи в мережі за допомогою Fabric API.
authors:
  - Daomephsta
  - dicedpixels
  - Earthcomputer
  - FlooferLand
  - FxMorin
  - i509VCB
  - modmuss50
  - natanfudge
  - NetUserGet
  - NShak
  - parzivail
  - skycatminepokie
  - SolidBlock-cn
  - Voleil
  - Wxffel
  - YTG123-Mods
  - zulrang
---

Мережа в Minecraft використовується для того, щоб клієнт і сервер могли спілкуватися один з одним. Мережа– це широка тема,
тому ця сторінка поділена на кілька категорій.

## Чому мережа важлива? {#why-is-networking-important}

Важливість мережі можна показати на простому прикладі коду.

:::warning
Наведений нижче код призначений лише для демонстрації.
:::

Скажімо, у вас є жезл, який підсвічує блок, який ви шукаєте, і який буде видно всім найближчі гравці:

```java
public class HighlightingWandItem extends Item {
    public HighlightingWandItem(Item.Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockPos target = ...

        // BAD CODE: DON'T EVER DO THIS! // [!code error]
        ClientBlockHighlighting.highlightBlock(MinecraftClient.getInstance(), target); // [!code error]
        return super.use(world, user, hand);
    }
}
```

Під час тестування ви побачите, як викликається блискавка, і нічого не ламається. Тепер ви хочете показати мод своєму
друже, ви завантажуєте виділений сервер і запрошуєте свого друга з установленим модом.

Ви використовуєте предмет, і сервер виходить з ладу. Ймовірно, ви помітите в журналі збоїв подібну помилку:

```log
[Server thread/FATAL]: Error executing task on Server
java.lang.RuntimeException: Cannot load class net.minecraft.client.MinecraftClient in environment type SERVER
```

### Чому сервер падає? {#why-does-the-server-crash}

Код викликає логіку, присутню лише в клієнтському дистрибутиві Minecraft. Причина розповсюдження Mojang
гра таким чином полягає в тому, щоб зменшити розмір JAR-файлу Minecraft Server. Насправді немає причин включати
весь механізм візуалізації, коли ваша власна машина візуалізує світ.

У середовищі розробки клієнтські класи позначаються анотацією `@Environment(EnvType.CLIENT)`.

### Як ми виправимо збій? {#how-do-we-fix-the-crash}

Щоб розв'язати цю проблему, вам потрібно зрозуміти, як взаємодіють ігровий клієнт і виділений сервер:

![Сторони](/assets/develop/networking/sides.png)

На діаграмі вище показано, що ігровий клієнт і виділений сервер є окремими системами, з’єднаними разом за допомогою моста
_пакети_. Пакети можуть містити дані, які ми називаємо _корисним навантаженням_.

Цей пакетний міст існує не лише між ігровим клієнтом і виділеним сервером, а й між вашим клієнтом і
інший клієнт, підключений через локальну мережу. Пакетний міст також присутній навіть в грі наодинці. Це пояснюється тим, що ігровий
клієнт запускає спеціальний інтегрований екземпляр сервера для запуску гри.

Підключення до сервера через локальну мережу або гру наодинці також можна розглядати як сервер як віддалений виділений сервер; так
ваш ігровий клієнт не може отримати прямий доступ до примірника сервера.

## Вступ до мережі {#an-introduction-to-networking}

### Визначення корисного навантаження {#defining-a-payload}

Це можна зробити, створивши Java `Record` з параметром `BlockPos`, який реалізує `CustomPayload`.

@[code lang=java transcludeWith=:::summon_Lightning_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Водночас ми визначили:

- `Ідентифікатор`, який використовується для ідентифікації корисного навантаження нашого пакета. Для цього прикладу нашим ідентифікатором буде
  `fabric-docs-reference:summon_lightning`.

@[code lang=java transclude={13-13}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Загальнодоступний статичний екземпляр `CustomPayload.Id` для унікальної ідентифікації цього спеціального корисного навантаження. Ми будемо посилатися на це
  ID як у нашому загальному, так і в клієнтському коді.

@[code lang=java transclude={14-14}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Загальнодоступний статичний екземпляр `PacketCodec`, щоб гра знала, як серіалізувати/десеріалізувати вміст
  пакету.

@[code lang=java transclude={15-15}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Ми також замінили `getId`, щоб повернути наш ідентифікатор корисного навантаження.

@[code lang=java transclude={17-20}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

### Реєстрація корисного навантаження {#registering-a-payload}

Перш ніж надіслати пакет із нашим власним корисним навантаженням, нам потрібно його зареєструвати.

:::info
`S2C` і `C2S` — два загальні суфікси, які означають _Server-to-Client_ і _Client-to-Server_ відповідно.
:::

Це можна зробити в нашому **загальному ініціалізаторі** за допомогою `PayloadTypeRegistry.playS2C().register`, який приймає
`CustomPayload.Id` і `PacketCodec`.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Подібний метод існує для реєстрації корисних даних клієнт-сервер: `PayloadTypeRegistry.playC2S().register`.

### Надсилання пакета клієнту {#sending-a-packet-to-the-client}

Щоб надіслати пакет із нашим власним корисним навантаженням, ми можемо використати `ServerPlayNetworking.send`, який приймає `ServerPlayerEntity`
і `CustomPayload`.

Почнемо зі створення нашого предмета, блискавичного татера. Ви можете замінити `use`, щоб активувати дію під час використання елемента.
У цьому випадку надішлімо пакети гравцям у світі серверів.

@[code lang=java transcludeWith=:::lightning_tater_item](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Розгляньмо код вище.

Ми надсилаємо пакети лише тоді, коли дію розпочато на сервері, повертаючись раніше з перевіркою `isClient`:

@[code lang=java transclude={22-24}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Ми створюємо екземпляр корисного навантаження з позицією користувача:

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Нарешті, ми отримуємо гравців у світі сервера через `PlayerLookup` і надсилаємо пакет кожному гравцеві.

@[code lang=java transclude={28-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

:::info
API Fabric надає `PlayerLookup`, набір допоміжних функцій, які шукатимуть гравців на сервері.

Для опису функціональності цих методів часто використовується термін «_відстеження_». Це означає, що сутність або блок на сервері відомі клієнту гравця (в межах їх видимості), і сутність або блокова сутність повинні повідомити
відстеження змін клієнтів.

Відстеження є важливою концепцією для ефективної роботи в мережі, щоб лише необхідні гравці отримували повідомлення про зміни шляхом надсилання пакетів.
:::

### Отримання пакета на клієнті {#receiving-a-packet-on-the-client}

Щоб отримати пакет, надісланий із сервера на клієнті, вам потрібно вказати, як ви будете обробляти вхідний пакет.

Це можна зробити в **ініціалізаторі клієнта**, викликавши `ClientPlayNetworking.registerGlobalReceiver` і передавши `CustomPayload.Id` і `PlayPayloadHandler`, який є функціональним інтерфейсом.

У цьому випадку ми визначимо дію, яка буде запускатися в реалізації реалізації `PlayPayloadHandler` (як
лямбда-вираз).

@[code lang=java transcludeWith=:::client_global_receiver](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Розгляньмо код вище.

Ми можемо отримати доступ до даних із нашого корисного навантаження, викликавши методи отримання запису. У цьому випадку `payload.pos()`. Який потім
можна використовувати для отримання позицій "x", "y" і "z".

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Нарешті ми створюємо `LightningEntity` і додаємо його до світу.

@[code lang=java transclude={33-38}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Тепер, якщо ви додасте цей мод на сервер і коли гравець використовує наш блискавичний татер, кожен гравець побачить блискавку
вражаючи позицію користувача.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm">Викликати блискавку за допомогою блискавичного татера</VideoPlayer>

### Надсилання пакета на сервер {#sending-a-packet-to-the-server}

Подібно до надсилання пакета клієнту, ми починаємо зі створення спеціального корисного навантаження. Цього разу, коли гравець використовує
отруйну картоплю на живій істоті, ми просимо сервер застосувати до неї ефект сяяння.

@[code lang=java transcludeWith=:::give_glowing_effect_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectC2SPayload.java)

Ми передаємо відповідний кодек разом із посиланням на метод, щоб отримати значення із запису для створення цього кодека.

Потім ми реєструємо наше корисне навантаження в нашому **загальному ініціалізаторі**. Однак цього разу як корисне навантаження _клієнт-сервер_ за допомогою
`PayloadTypeRegistry.playC2S().register`.

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Щоб надіслати пакет, додаймо дію, коли гравець використовує отруйну картоплю. Ми будемо використовувати `UseEntityCallback`
подія, щоб зберегти речі стисло.

Ми реєструємо подію в нашому **ініціалізаторі клієнта** та використаємо `isClient()`, щоб гарантувати, що дія лише запускається
на логічному клієнті.

@[code lang=java transcludeWith=:::use_entity_callback](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Ми створимо екземпляр нашого `GiveGlowingEffectC2SPayload` з необхідними аргументами. У цьому випадку ідентифікатор мережі
з
цільової сутності.

@[code lang=java transclude={51-51}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Нарешті, ми надсилаємо пакет на сервер, викликаючи `ClientPlayNetworking.send` з примірником нашого
`GiveGlowingEffectC2SPayload`.

@[code lang=java transclude={52-52}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

### Отримання пакета на сервері {#receiving-a-packet-on-the-server}

Це можна зробити в **загальному ініціалізаторі**, викликавши `ServerPlayNetworking.registerGlobalReceiver` і передавши
`CustomPayload.Id` і `PlayPayloadHandler`.

@[code lang=java transcludeWith=:::server_global_receiver](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

:::info
Важливо, щоб ви перевірили вміст пакета на стороні сервера.

У цьому випадку ми перевіряємо, чи існує сутність, на основі її ідентифікатора мережі.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Крім того, цільова сутність має бути живою істотою, і ми обмежуємо діапазон цільової сутності від
гравця до 5.

@[code lang=java transclude={32-32}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)
:::

Тепер, коли будь-який гравець намагатиметься використати отруйну картоплю на живій істоті, до нього буде застосовано ефект світіння.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm">Сяйний ефект застосовується, коли отруйна картопля використовується на живій істоті</VideoPlayer>
