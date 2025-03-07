---
title: Networking
description: A general guide on networking using Fabric API.
authors:
  - daomephsta
  - dicedpixels
  - earthcomputer
  - flooferland
  - fxmorin
  - i509vcb
  - modmuss50
  - natanfudge
  - netuserget
  - nshak
  - parzivail
  - skycatminepokie
  - solidblock
  - voleil
  - wxffel
  - ytg123-mods
  - zulrang
---

Networking in Minecraft is used so the client and server can communicate with each other. Networking is a broad topic,
so this page is split up into a few categories.

## Why Is Networking Important? {#why-is-networking-important}

The importance of networking can be shown by a simple code example.

::: warning
Below code is for demonstration purposes only.
:::

Say you had a Wand which highlights the block you're looking, which will be visible to all
nearby players:

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

Upon testing, you will see a lightning bolt being summoned and nothing crashes. Now you want to show the mod to your
friend, you boot up a dedicated server and invite your friend on with the mod installed.

You use the item and the server crashes. You will probably notice in the crash log an error similar to this:

```log
[Server thread/FATAL]: Error executing task on Server
java.lang.RuntimeException: Cannot load class net.minecraft.client.MinecraftClient in environment type SERVER
```

### Why Does the Server Crash? {#why-does-the-server-crash}

The code calls logic only present on the client distribution of the Minecraft. The reason for Mojang distributing the
game in this way is to cut down on the size of the Minecraft Server JAR file. There isn't really a reason to include an
entire rendering engine when your own machine will render the world.

In a development environment, client-only classes are indicated by the `@Environment(EnvType.CLIENT)` annotation.

### How Do We Fix the Crash? {#how-do-we-fix-the-crash}

To fix this issue, you need to understand how the game client and dedicated server communicate:

![Sides](/assets/develop/networking/sides.png)

The diagram above shows that the game client and dedicated server are separate systems, bridged together using
_packets_. Packets can contain data which we refer to as the _payload_.

This packet bridge does not only exist between a game client and dedicated server, but also between your client and
another client connected over LAN. The packet bridge is also present even in singleplayer. This is because the game
client will spin up a special integrated server instance to run the game on.

Connection to a server over LAN or singleplayer can also be treated like the server is a remote, dedicated server; so
your game client can't directly access the server instance.

## An Introduction to Networking {#an-introduction-to-networking}

### Defining a Payload {#defining-a-payload}

This can be done by creating a Java `Record` with a `BlockPos` parameter that implements `CustomPayload`.

@[code lang=java transcludeWith=:::summon_Lightning_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

At the same time, we've defined:

- An `Identifier` used to identify our packet's payload. For this example our identifier will be
  `fabric-docs-reference:summon_lightning`.

@[code lang=java transclude={13-13}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- A public static instance of `CustomPayload.Id` to uniquely identify this custom payload. We will be referencing this
  ID in both our common and client code.

@[code lang=java transclude={14-14}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- A public static instance of a `PacketCodec` so that the game knows how to serialize/deserialize the contents of the
  packet.

@[code lang=java transclude={15-15}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

We have also overridden `getId` to return our payload ID.

@[code lang=java transclude={17-20}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

### Registering a Payload {#registering-a-payload}

Before we send a packet with our custom payload, we need to register it.

::: info
`S2C` and `C2S` are two common suffixes that mean _Server-to-Client_ and _Client-to-Server_ respectively.
:::

This can be done in our **common initializer** by `PayloadTypeRegistry.playS2C().register` which takes in a
`CustomPayload.Id` and a `PacketCodec`.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

A similar method exists to register client-to-server payloads: `PayloadTypeRegistry.playC2S().register`.

### Sending a Packet to the Client {#sending-a-packet-to-the-client}

To send a packet with our custom payload, we can use `ServerPlayNetworking.send` which takes in a `ServerPlayerEntity`
and a `CustomPayload`.

Let's start by creating our Lightning Tater item. You can override `use` to add trigger an action when the item is used.
In this case, let's send packets to all the players in the server world.

@[code lang=java transcludeWith=:::lightning_tater_item](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Let's examine the code above.

We only send packets when the action is initiated on the server, by returning early with a `isClient` check:

@[code lang=java transclude={22-24}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

We create an instance of the payload with the user's position:

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Finally, we get all the players in the server world through `PlayerLookup` and send a packet to each player.

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

@[code lang=java transcludeWith=:::client_global_receiver](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Let's examine the code above.

We can access the data from our payload by calling the Record's getter methods. In this case `payload.pos()`. Which then
can be used to get the `x`, `y` and `z` positions.

@[code lang=java transclude={31-31}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Finally, we create a `LightningEntity` and add it to the world.

@[code lang=java transclude={32-37}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Now, if you add this mod to a server and when a player uses our Lightning Tater item, every player will see lightning
striking at the user's position.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm" title="Summon lightning using Lightning Tater" />

### Sending a Packet to the Server {#sending-a-packet-to-the-server}

Just like sending a packet to the client, we start by creating a custom payload. This time, when a player uses a
Poisonous Potato on a living entity, we request the server to apply the Glowing effect to it.

@[code lang=java transcludeWith=:::give_glowing_effect_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectC2SPayload.java)

We pass in the appropriate codec along with a method reference to get the value from the Record to build this codec.

Then we register our payload in our **common initializer**. However, this time as _Client-to-Server_ payload by using
`PayloadTypeRegistry.playC2S().register`.

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

To send a packet, let's add an action when the player uses a Poisonous Potato. We'll be using the `UseEntityCallback`
event to
keep things concise.

We register the event in our **client initializer**, and we use `isClient()` to ensure that the action is only triggered
on the logical client.

@[code lang=java transcludeWith=:::use_entity_callback](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

We create an instance of our `GiveGlowingEffectC2SPayload` with the necessary arguments. In this case, the network ID
of
the targeted entity.

@[code lang=java transclude={50-50}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Finally, we send a packet to the server by calling `ClientPlayNetworking.send` with the instance of our
`UsePoisonousPotatoPayload`.

@[code lang=java transclude={51-51}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

### Receiving a Packet on the Server {#receiving-a-packet-on-the-server}

This can be done in the **common initializer**, by calling `ServerPlayNetworking.registerGlobalReceiver` and passing a
`CustomPayload.Id` and a `PlayPayloadHandler`.

@[code lang=java transcludeWith=:::server_global_receiver](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

::: info
It is important that you validate the content of the packet on the server side.

In this case, we validate if the entity exists based on its network ID.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Additionally, the targeted entity has to be a living entity, and we restrict the range of the target entity from the
player to 5.

@[code lang=java transclude={32-32}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)
:::

Now when any player tries to use a Poisonous Potato on a living entity, the glowing effect will be applied to it.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm" title="Glowing effect is applied when a Poisonous Potato is used on a living entity." />
