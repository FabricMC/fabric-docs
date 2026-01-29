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

Мережа в Minecraft використовується для того, щоб клієнт і сервер могли спілкуватися один з одним. Мережа — це широка тема,
тому ця сторінка поділена на кілька категорій.

## Чому мережа важлива? {#why-is-networking-important}

Пакети — це основна концепція мережі в Minecraft.
Пакети складаються з довільних даних, які можуть надсилатися від сервера до клієнта і навпаки.
Ознайомтеся з наведеною нижче схемою, яка наочно демонструє мережеву архітектуру в Fabric:

![Бокова архітектура](/assets/develop/networking/sides.png)

Зверніть увагу, що пакети є мостом між сервером і клієнтом; це тому, що майже все, що ви робите в грі, певним чином включає мережу, знаєте ви це чи ні.
Наприклад, коли ви надсилаєте повідомлення чату, на сервер надсилається пакет із вмістом.
Потім сервер надсилає ще один пакет усім іншим клієнтам із вашим повідомленням.

Пам’ятайте про одну важливу річ: сервер завжди працює, навіть у грі наодинці та LAN. Пакети все ще використовуються для обміну даними
клієнту і сервера, навіть якщо ніхто інший не грає з вами. Коли говорять про сторони в мережі, використовуються терміни «**логічний** клієнт» і «**логічний** сервер». Інтегрований сервер для гри наодинці/LAN та виділений сервер є **логічними** серверами, але лише виділений сервер можна вважати **фізичним** сервером.

Коли стан між клієнтом і сервером не синхронізовано, ви можете зіткнутися з проблемами, через які сервер або інші клієнти не погоджуються з тим, що робить інший клієнт. Це часто називають «десинхронізацією». Під час написання власного моду вам може знадобитися надіслати пакет даних, щоб синхронізувати стан сервера та всіх клієнтів.

## Вступ до мережі {#an-introduction-to-networking}

### Визначення корисного навантаження {#defining-a-payload}

::: info

Корисне навантаження — це дані, які надсилаються в пакеті.

:::

Це можна зробити, створивши Java `Record` з параметром `BlockPos`, який реалізує `CustomPacketPayload`.

@[code lang=java transcludeWith=:::summon_Lightning_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Водночас ми визначили:

- `Identifier`, який використовується для ідентифікації корисного навантаження нашого пакета. Для цього прикладу нашим ідентифікатором буде
  `example-mod:summon_lightning`.

@[code lang=java transclude={13-13}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Загальнодоступний статичний екземпляр `CustomPayload.Id` для унікальної ідентифікації цього спеціального корисного навантаження. Ми будемо посилатися на цей ID як у нашому загальному, так і в клієнтському коді.

@[code lang=java transclude={14-14}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Загальнодоступний статичний екземпляр `PacketCodec`, щоб гра знала, як серіалізувати/десеріалізувати вміст пакета.

@[code lang=java transclude={15-15}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Ми також замінили `type`, щоб повернути наш ідентифікатор корисного навантаження.

@[code lang=java transclude={17-20}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

### Реєстрація корисного навантаження {#registering-a-payload}

Перш ніж ми надішлемо пакет із нашим власним корисним навантаженням, нам потрібно зареєструвати його на обох фізичних сторонах.

::: info

`S2C` і `C2S` — два загальні суфікси, які означають _із сервера до клієнта_ (_Server-to-Client_) та _з клієнта до сервера_ (_Client-to-Server_) відповідно.

:::

Це можна зробити в нашому **загальному ініціалізаторі** за допомогою `PayloadTypeRegistry.playS2C().register`, який приймає `CustomPayload.Id` і `StreamCodec`.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

Подібний метод існує для реєстрації корисних даних з клієнта до сервера: `PayloadTypeRegistry.playC2S().register`.

### Надсилання пакета клієнту {#sending-a-packet-to-the-client}

Щоб надіслати пакет із нашим власним корисним навантаженням, ми можемо використати `ServerPlayNetworking.send`, який приймає `ServerPlayerEntity` і `CustomPayload`.

Почнемо зі створення нашого предмета, Lightning Tater. Ви можете замінити `use`, щоб активувати дію під час використання предмета.
У цьому випадку надішлімо пакети гравцям у рівень (світ) сервера.

@[code lang=java transcludeWith=:::lightning_tater_item](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Розгляньмо код вище.

Ми надсилаємо пакети лише тоді, коли дію розпочато на сервері, повертаючись раніше з перевіркою `isClient`:

@[code lang=java transclude={22-24}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Ми створюємо екземпляр корисного навантаження з позицією користувача:

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Нарешті, ми отримуємо гравців у рівні (світі) сервера через `PlayerLookup` і надсилаємо пакет кожному гравцеві.

@[code lang=java transclude={28-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

::: info

API Fabric надає `PlayerLookup`, набір допоміжних функцій, які шукатимуть гравців на сервері.

Для опису функціональності цих методів часто використовується термін «_відстеження_». Це означає, що сутність або чанк на сервері відомі клієнту гравця (в межах їх видимості), і сутність або блок-сутність повинні повідомити відстеження змін клієнтів.

Відстеження є важливою концепцією для ефективної роботи в мережі, щоб лише необхідні гравці отримували повідомлення про зміни шляхом надсилання пакетів.

:::

### Отримання пакета на клієнті {#receiving-a-packet-on-the-client}

Щоб отримати пакет, надісланий із сервера до клієнта, вам потрібно вказати, як ви будете обробляти вхідний пакет.

Це можна зробити в **ініціалізаторі клієнта**, викликавши `ClientPlayNetworking.registerGlobalReceiver` і передавши `CustomPayload.Id` і `PlayPayloadHandler`, який є функціональним інтерфейсом.

У цьому випадку ми визначимо дію, яка буде запускатися в реалізації `PlayPayloadHandler` (як лямбда-вираз).

@[code lang=java transcludeWith=:::client_global_receiver](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Розгляньмо код вище.

Ми можемо отримати доступ до даних із нашого корисного навантаження, викликавши методи отримання запису. У цьому випадку `payload.pos()`. Який потім можна використовувати для отримання позицій `x`, `y` і `z`.

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Нарешті ми створюємо `LightningBolt` і додаємо його до рівня (світу).

@[code lang=java transclude={33-38}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Тепер, якщо ви додасте цей мод на сервер і коли гравець використовує наш Lightning Tater, кожен гравець побачить блискавку вражаючи позицію користувача.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm">Виклик блискавки за допомогою Lightning Tater</VideoPlayer>

### Надсилання пакета до сервера {#sending-a-packet-to-the-server}

Подібно до надсилання пакета клієнту, ми починаємо зі створення спеціального корисного навантаження. Цього разу, коли гравець використовує отруйну картоплю на живій істоті, ми просимо сервер застосувати до неї ефект світіння.

@[code lang=java transcludeWith=:::give_glowing_effect_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectC2SPayload.java)

Ми передаємо відповідний кодек разом із посиланням на метод, щоб отримати значення із запису для створення цього кодека.

Потім ми реєструємо наше корисне навантаження в нашому **загальному ініціалізаторі**. Однак цього разу як корисне навантаження _із клієнта до сервера_ за допомогою
`PayloadTypeRegistry.playC2S().register`.

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

Щоб надіслати пакет, додаймо дію, коли гравець використовує отруйну картоплю. Ми будемо використовувати подію `UseEntityCallback`, щоб зберігати речі стисло.

Ми реєструємо подію в нашому **ініціалізаторі клієнта** та використаємо `isClientSide()`, щоб гарантувати, що дія лише запускається на логічному клієнті.

@[code lang=java transcludeWith=:::use_entity_callback](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Ми створимо екземпляр нашого `GiveGlowingEffectC2SPayload` з необхідними аргументами. У цьому випадку ID мережі цільової сутності.

@[code lang=java transclude={51-51}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Нарешті, ми надсилаємо пакет на сервер, викликаючи `ClientPlayNetworking.send` з екземпляром нашого `GiveGlowingEffectC2SPayload`.

@[code lang=java transclude={52-52}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

### Отримання пакета на сервері {#receiving-a-packet-on-the-server}

Це можна зробити в **загальному ініціалізаторі**, викликавши `ServerPlayNetworking.registerGlobalReceiver` і передавши `CustomPayload.Id` і `PlayPayloadHandler`.

@[code lang=java transcludeWith=:::server_global_receiver](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

::: info

Важливо, щоб ви перевірили вміст пакета на стороні сервера.

У цьому випадку ми перевіряємо, чи існує сутність, на основі її ID мережі.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

Крім того, цільова сутність має бути живою сутністю, і ми обмежуємо діапазон цільової сутності від гравця до 5.

@[code lang=java transclude={32-32}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

:::

Тепер, коли будь-який гравець намагатиметься використати отруйну картоплю на живій істоті, до нього буде застосовано ефект світіння.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm">Ефект світіння застосовується, коли отруйна картопля використовується на живій сутності</VideoPlayer>
