---
title: Networking
description: A general guide on networking using Fabric API.
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

Networking in Minecraft is used so the client and server can communicate with each other. Networking is a broad topic,
so this page is split up into a few categories.

## Why Is Networking Important? {#why-is-networking-important}

Packets are the core concept of networking in Minecraft.
Packets are made up of arbitrary data that can be sent either from server to client or from client to server.
Check out the diagram below, which provides a visual representation of the networking architecture in Fabric:

![Sided Architecture](/assets/develop/networking/sides.png)

Notice how packets are the bridge between the server and the client; that's because almost everything you do in the game involves networking in some way, whether you know it or not.
For example, when you send a chat message, a packet is sent to the server with the content.
The server then sends another packet to all the other clients with your message.

One important thing to keep in mind is there is always a server running, even in singleplayer and LAN. Packets are still used to communicate between
the client and server even when no one else is playing with you. When talking about sides in networking, the terms "**logical** client" and "**logical** server" are used. The integrated singleplayer/LAN server and the dedicated server are both **logical** servers, but only the dedicated server can be considered a **physical** server.

When state is not synced between the client and server, you can run into issues where the server or other clients don't agree with what another
client is doing. This is often known as a "desync". When writing your own mod you may need to send a packet of data to keep the state of the server
and all clients in sync.

## An Introduction to Networking {#an-introduction-to-networking}

### Defining a Payload {#defining-a-payload}

::: info

A payload is the data that is sent within a packet.

:::

This can be done by creating a Java `Record` with a `BlockPos` parameter that implements `CustomPacketPayload`.

@[code lang=java transcludeWith=:::summon_Lightning_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

At the same time, we've defined:

- An `Identifier` used to identify our packet's payload. For this example our identifier will be
  `example-mod:summon_lightning`.

@[code lang=java transclude={13-13}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- A public static instance of `CustomPayload.Id` to uniquely identify this custom payload. We will be referencing this
  ID in both our common and client code.

@[code lang=java transclude={14-14}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- A public static instance of a `StreamCodec` so that the game knows how to serialize/deserialize the contents of the
  packet.

@[code lang=java transclude={15-15}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

We have also overridden `type` to return our payload ID.

@[code lang=java transclude={17-20}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

### Registering a Payload {#registering-a-payload}

Before we send a packet with our custom payload, we need to register it on both physical sides.

::: info

`S2C` and `C2S` are two common suffixes that mean _Server-to-Client_ and _Client-to-Server_ respectively.

:::

This can be done in our **common initializer** by using `PayloadTypeRegistry.playS2C().register` which takes in a
`CustomPayload.Id` and a `StreamCodec`.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

A similar method exists to register client-to-server payloads: `PayloadTypeRegistry.playC2S().register`.

### Sending a Packet to the Client {#sending-a-packet-to-the-client}

To send a packet with our custom payload, we can use `ServerPlayNetworking.send` which takes in a `ServerPlayerEntity`
and a `CustomPayload`.

Let's start by creating our Lightning Tater item. You can override `use` to trigger an action when the item is used.
In this case, let's send packets to the players in the server level.

@[code lang=java transcludeWith=:::lightning_tater_item](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Let's examine the code above.

We only send packets when the action is initiated on the server, by returning early with a `isClient` check:

@[code lang=java transclude={22-24}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

We create an instance of the payload with the user's position:

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Finally, we get the players in the server level through `PlayerLookup` and send a packet to each player.

@[code lang=java transclude={28-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

::: info

Fabric API provides `PlayerLookup`, a collection of helper functions that will look up players in a server.

A term frequently used to describe the functionality of these methods is "_tracking_". It means that an entity or a
chunk
on the server is known to a player's client (within their view distance) and the entity or block entity should notify
tracking clients of changes.

Tracking is an important concept for efficient networking, so that only the necessary players are notified of changes by
sending packets.

:::

### Receiving a Packet on the Client {#receiving-a-packet-on-the-client}

To receive a packet sent from a server on the client, you need to specify how you will handle the incoming packet.

This can be done in the **client initializer**, by calling `ClientPlayNetworking.registerGlobalReceiver` and passing a
`CustomPayload.Id` and a `PlayPayloadHandler`, which is a Functional Interface.

In this case, we'll define the action to trigger within the implementation of `PlayPayloadHandler` implementation (as a
lambda expression).

@[code lang=java transcludeWith=:::client_global_receiver](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Let's examine the code above.

We can access the data from our payload by calling the Record's getter methods. In this case `payload.pos()`. Which then
can be used to get the `x`, `y` and `z` positions.

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Finally, we create a `LightningBolt` and add it to the level.

@[code lang=java transclude={33-38}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Now, if you add this mod to a server and when a player uses our Lightning Tater item, every player will see lightning
striking at the user's position.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm">Summon lightning using Lightning Tater</VideoPlayer>

### Sending a Packet to the Server {#sending-a-packet-to-the-server}

Just like sending a packet to the client, we start by creating a custom payload. This time, when a player uses a
Poisonous Potato on a living entity, we request the server to apply the Glowing effect to it.

@[code lang=java transcludeWith=:::give_glowing_effect_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectC2SPayload.java)

We pass in the appropriate codec along with a method reference to get the value from the Record to build this codec.

Then we register our payload in our **common initializer**. However, this time as _Client-to-Server_ payload by using
`PayloadTypeRegistry.playC2S().register`.

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

To send a packet, let's add an action when the player uses a Poisonous Potato. We'll be using the `UseEntityCallback`
event to
keep things concise.

We register the event in our **client initializer**, and we use `isClientSide()` to ensure that the action is only triggered
on the logical client.

@[code lang=java transcludeWith=:::use_entity_callback](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

We create an instance of our `GiveGlowingEffectC2SPayload` with the necessary arguments. In this case, the network ID
of
the targeted entity.

@[code lang=java transclude={51-51}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

Finally, we send a packet to the server by calling `ClientPlayNetworking.send` with the instance of our
`GiveGlowingEffectC2SPayload`.

@[code lang=java transclude={52-52}](@/reference/latest/src/client/java/com/example/docs/network/basic/ExampleModNetworkingBasicClient.java)

### Receiving a Packet on the Server {#receiving-a-packet-on-the-server}

This can be done in the **common initializer**, by calling `ServerPlayNetworking.registerGlobalReceiver` and passing a
`CustomPayload.Id` and a `PlayPayloadHandler`.

@[code lang=java transcludeWith=:::server_global_receiver](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

::: info

It is important that you validate the content of the packet on the server side.

In this case, we validate if the entity exists based on its network ID.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

Additionally, the targeted entity has to be a living entity, and we restrict the range of the target entity from the
player to 5.

@[code lang=java transclude={32-32}](@/reference/latest/src/main/java/com/example/docs/networking/basic/ExampleModNetworkingBasic.java)

:::

Now when any player tries to use a Poisonous Potato on a living entity, the glowing effect will be applied to it.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm">Glowing effect is applied when a Poisonous Potato is used on a living entity</VideoPlayer>
