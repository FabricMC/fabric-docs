---
title: Сетевое взаимодействие
description: Общее руководство по работе с сетью с использованием Fabric API.
authors:
  - bluebear94
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

Сетевое взаимодействие в Minecraft используется для связи между клиентом и сервером. Это обширная тема, поэтому данная страница разделена на несколько категорий.

## Почему сетевое взаимодействие важно? {#why-is-networking-important}

Пакеты — это ключевое понятие сетевого взаимодействия в Minecraft.
Пакеты состоят из произвольных данных, которые могут передаваться как от сервера к клиенту, так и от клиента к серверу.
Ознакомьтесь со схемой ниже, которая наглядно иллюстрирует архитектуру сети в Fabric:

![Боковая архитектура](/assets/develop/networking/sides.png)

Обратите внимание, что пакеты служат мостом между сервером и клиентом. Это связано с тем, что почти любое ваше действие в игре так или иначе задействует сеть, знаете вы об этом или нет.
Например, когда вы отправляете сообщение в чат, на сервер уходит пакет с его содержимым.
Затем сервер отправляет другой пакет со средством связи всем остальным клиентам.

Важно помнить, что сервер работает всегда — даже в одиночной игре и в мире для локальной сети (LAN). Пакеты всё равно используются для связи между клиентом и сервером, даже если с вами никто больше не играет. Когда речь заходит о сторонах в контексте сети, используются термины "**логический** клиент" (logical client) и **"логический** сервер" (logical server). Встроенный сервер одиночной/локальной игры и выделенный сервер являются **логическими** серверами, но только выделенный сервер можно считать **физическим** сервером.

Когда состояние между клиентом и сервером не синхронизировано, могут возникать проблемы, при которых сервер или другие клиенты «не согласны» с действиями конкретного клиента. Это явление часто называют «рассинхронизацией» (desync). При написании собственного мода вам может потребоваться отправить пакет данных, чтобы синхронизировать состояние сервера и всех клиентов.

## Введение в сетевое взаимодействие {#an-introduction-to-networking}

### Определение полезной нагрузки (Payload) {#defining-a-payload}

::: info

Полезная нагрузка (payload) — это данные, которые переносятся внутри пакета.

:::

Это можно сделать путем создания Java-записи (`Record`) с параметром `BlockPos`, реализующей интерфейс `CustomPacketPayload`.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#summon_lightning_payload

В то же время мы определили:

- Идентификатор (`Identifier`), используемый для распознавания полезной нагрузки нашего пакета. В данном примере наш идентификатор будет `example-mod:summon_lightning`.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#identifier

- Открытый статический экземпляр (public static) `CustomPayload.Type` для уникальной идентификации этой пользовательской полезной нагрузки. Мы будем ссылаться на этот ID как в общем коде (common), так и в клиентском (client).

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#payload_type

- Открытый статический экземпляр (public static) `StreamCodec`, чтобы игра знала, как сериализовать/десериализовать содержимое пакета.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#stream_codec

Мы также переопределили метод `type`, чтобы он возвращал ID нашей полезной нагрузки.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ClientboundSummonLightningPayload.java#type

### Регистрация полезной нагрузки {#registering-a-payload}

Прежде чем отправить пакет с нашей полезной нагрузкой, нам необходимо зарегистрировать её на обеих физических сторонах.

Это можно сделать в нашем **общем инициализаторе** (common initializer) с помощью метода `PayloadTypeRegistry.clientboundPlay().register`, который принимает `CustomPayload.Type` и `StreamCodec`.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#clientbound

Аналогичный метод существует для регистрации полезной нагрузки, отправляемой от клиента к серверу: `PayloadTypeRegistry.serverboundPlay().register`.

### Отправка пакета клиенту {#sending-a-packet-to-the-client}

Чтобы отправить пакет с нашей полезной нагрузкой, мы можем использовать метод `ServerPlayNetworking.send`, который принимает `ServerPlayer` и `CustomPayload`.

Давайте начнем с создания нашего предмета «Молниеносная картошка» (Lightning Tater). Вы можете переопределить метод use, чтобы запускать действие при использовании предмета.
В данном случае давайте отправим пакеты игрокам на данном уровне сервера (server level).

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#lightning_tater_item

Давайте рассмотрим приведенный выше код.

Мы отправляем пакеты только тогда, когда действие инициировано на сервере, выполняя ранний возврат (return) с помощью проверки `isClientSide()`:

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#client_check

Мы создаем экземпляр полезной нагрузки с позицией пользователя:

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#payload_instance

Наконец, мы получаем список игроков на уровне сервера через `PlayerLookup` и отправляем пакет каждому игроку.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java#lookup

::: info

Fabric API предоставляет `PlayerLookup` — набор вспомогательных функций для поиска игроков на сервере.

Термин, часто используемый для описания функционала этих методов — «_трекинг_» (tracking, отслеживание). Это означает, что объект или чанк на сервере известен клиенту игрока (на расстоянии его обзора), и сущность или блок должны уведомлять отслеживающих клиентов об изменениях.

Трекинг — важная концепция для оптимизации сетевого взаимодействия. Благодаря ему о произошедших изменениях путем отправки пакетов уведомляются только те игроки, которым это необходимо.

:::

### Получение пакета на стороне клиента {#receiving-a-packet-on-the-client}

Чтобы получить пакет, отправленный с сервера на клиент, вам необходимо указать, как именно вы будете обрабатывать входящий пакет.

Это можно сделать в **инициализаторе клиента** (client initializer), вызвав метод `ClientPlayNetworking.registerGlobalReceiver` и передав туда `CustomPayload.Type` и `PlayPayloadHandler`, который является функциональным интерфейсом.

В данном случае мы определим запускаемое действие внутри реализации `PlayPayloadHandler` (в виде лямбда-выражения).

<<< @/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#client_global_receiver

Давайте рассмотрим приведенный выше код.

Мы можем получить доступ к данным из нашей полезной нагрузки (payload), вызвав геттеры записи (Record). В данном случае это `payload.pos()`, который затем можно использовать для получения координат X, Y и Z.

<<< @/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#payload_pos

Наконец, мы создаем сущность LightningBolt (молнию) и добавляем ее в мир (level).

<<< @/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#lightning_bolt

Теперь, если вы добавите этот мод на сервер, то при использовании игроком предмета Lightning Tater все игроки увидят удар молнии в позиции этого пользователя.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm">Вызов молнии предметом "Молниеносная картошка"</VideoPlayer>

### Отправка пакета на сервер {#sending-a-packet-to-the-server}

Как и в случае с отправкой пакета на клиент, мы начинаем с создания пользовательской полезной нагрузки (custom payload). На этот раз, когда игрок использует "Ядовитый картофель" на живой сущности, мы запрашиваем у сервера наложение на нее эффекта «Свечение» (Glowing).

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectServerboundPayload.java#give_glowing_effect_payload

Мы передаем соответствующий кодек (codec) вместе со ссылкой на метод (method reference) для получения значения из Record для сборки этого кодека.

Затем мы регистрируем нашу полезную нагрузку в **общем инициализаторе**. Однако на этот раз как пакет от клиента к серверу (_Client-to-Server_ payload), используя метод `PayloadTypeRegistry.serverboundPlay().register`.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#serverbound

Чтобы отправить пакет, давайте добавим действие при использовании игроком "Ядовитого картофеля". Мы будем использовать событие `UseEntityCallback`, чтобы код оставался лаконичным.

Мы регистрируем это событие в нашем **инициализаторе клиента** и используем `isClientSide()`, чтобы убедиться, что действие будет запущено только на логическом клиенте.

<<< @/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#use_entity_callback

Мы создаем экземпляр нашего класса `GiveGlowingEffectServerboundPayload` с необходимыми аргументами. В данном случае это сетевой ID (network ID) целевой сущности.

<<< @/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#payload

Наконец, мы отправляем пакет на сервер, вызывая метод `ClientPlayNetworking.send` с экземпляром нашей полезной нагрузки `GiveGlowingEffectServerboundPayload`.

<<< @/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java#send

### Получение пакета на сервере {#receiving-a-packet-on-the-server}

Это можно сделать в **обычном инициализаторе**, вызвав `ServerPlayNetworking.registerGlobalReceiver` и передав
`CustomPayload.Type` и `PlayPayloadHandler`.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#server_global_receiver

::: info

Очень важно проверять содержимое пакета на стороне сервера.

В данном случае мы проверяем, существует ли сущность, на основе ее сетевого ID.

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#validate_entity

Кроме того, целевая сущность должна быть живой сущностью (living entity), и мы ограничиваем расстояние от игрока до целевой сущности пятью блоками. Если эти условия будут выполнены, мы применим следующий эффект:

<<< @/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java#entity_checks

:::

Теперь, когда любой игрок попытается использовать "Ядовитый картофель" на живой сущности, к ней применится эффект свечения.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm">Эффект свечения накладывается, когда Ядовитый картофель используется на живое существо</VideoPlayer>
