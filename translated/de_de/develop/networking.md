---
title: Networking
description: Ein allgemeiner Leitfaden über Networking unter Verwendung der Fabric API.
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

In Minecraft wird Networking verwendet, damit Client und Server miteinander kommunizieren können. Networking ist ein weites Feld, daher ist diese Seite in mehrere Kategorien unterteilt.

## Warum ist Networking wichtig? {#why-is-networking-important}

Die Bedeutung von Networking lässt sich anhand eines einfachen Code-Beispiels verdeutlichen.

:::warning
Der nachstehende Code dient nur zu Demonstrationszwecken.
:::

Angenommen, du hättest einen Zauberstab, der den Block, den du ansiehst, hervorhebt – und das für alle nahegelegenen Spieler sichtbar macht:

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

Beim Testen wirst du sehen, dass ein Blitz beschworen wird und nichts abstürzt. Wenn du jetzt deinem Freund den Mod zeigen willst, startest du einen dedizierten Server und lädst deinen Freund mit dem installierten Mod ein.

Du verwendest das Item und der Server stürzt ab. Im Absturzprotokoll wird wahrscheinlich ein ähnlicher Fehler wie dieser angezeigt:

```log
[Server thread/FATAL]: Error executing task on Server
java.lang.RuntimeException: Cannot load class net.minecraft.client.MinecraftClient in environment type SERVER
```

### Warum stürzt der Server ab? {#why-does-the-server-crash}

Der Code ruft eine Logik auf, die nur in der Client-Distribution von Minecraft vorhanden ist. Der Grund für Mojang, das Spiel auf diese Weise zu verteilen, ist die Verringerung der Größe der Minecraft Server JAR-Datei. Es gibt eigentlich keinen Grund, eine ganze Rendering-Engine einzubinden, wenn dein eigener Rechner die Welt rendert.

In einer Entwicklungsumgebung werden reine Client-Klassen durch die Annotation `@Environment(EnvType.CLIENT)` gekennzeichnet.

### Wie beheben wir den Absturz? {#how-do-we-fix-the-crash}

Um dieses Problem zu beheben, musst du verstehen, wie der Spiel-Client und der dedizierte Server kommunizieren:

![Seiten](/assets/develop/networking/sides.png)

Das obige Diagramm zeigt, dass der Spiel-Client und der dedizierte Server separate Systeme sind, die über_Pakete_ verbunden sind. Pakete können Daten enthalten, die wir als _Payload_ bezeichnen.

Diese Paketbrücke besteht nicht nur zwischen einem Spielclient und einem dedizierten Server, sondern auch zwischen deinem Client und einem anderen über LAN verbundenen Client. Die Paketbrücke ist auch im Einzelspielermodus vorhanden. Der Grund dafür ist, dass der Spielclient eine spezielle integrierte Serverinstanz zum Ausführen des Spiels ausführt.

Die Verbindung zu einem Server über LAN oder den Einzelspielermodus kann auch so behandelt werden, als wäre der Server ein entfernter, dedizierter Server; Dein Spielclient kann also nicht direkt auf die Serverinstanz zugreifen.

## Eine Einführung in Networking {#an-introduction-to-networking}

### Einen Payload definieren {#defining-a-payload}

Dies kann durch das Erstellen eines Java `Record` mit einem `BlockPos`-Parameter, der `CustomPayload` implementiert, gelöst werden.

@[code lang=java transcludeWith=:::summon_Lightning_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Zugleich haben wir folgendes definiert:

- Einen `Identifier`, der zur Identifizierung des Payload unseres Pakets verwendet wird. In diesem Beispiel lautet der Bezeichner `fabric-docs-reference:summon_lightning`.

@[code lang=java transclude={13-13}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Eine öffentliche, statische Instanz von `CustomPayload.Id` zur eindeutigen Identifizierung dieses benutzdefinierten Payloads. Wir werden auf diese ID sowohl in unserem allgemeinen als auch in unserem Client-Code verweisen.

@[code lang=java transclude={14-14}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

- Eine öffentliche, statische Instanz eines `PacketCodec`, damit das Spiel weiß, wie es den Inhalt des Pakets serialisieren/deserialisieren kann.

@[code lang=java transclude={15-15}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

Wir haben auch `getId` überschrieben, um unsere Payload-ID zurückzugeben.

@[code lang=java transclude={17-20}](@/reference/latest/src/main/java/com/example/docs/networking/basic/SummonLightningS2CPayload.java)

### Einen Payload registrieren {#registering-a-payload}

Bevor wir ein Paket mit unserem benutzerdefinierten Payload senden, müssen wir ihn registrieren.

:::info
`S2C` und `C2S` sind zwei gängige Suffixe, die _Server-to-Client_ und _Client-to-Server_ bedeuten.
:::

Dies kann in unserem **gemeinsamen Initialisierers** mit Hilfe von `PayloadTypeRegistry.playS2C().register` erfolgen, das eine `CustomPayload.Id` und einen `PacketCodec` entgegennimmt.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Eine ähnliche Methode existiert, um Client-to-Server Payloads zu registrieren: `PayloadTypeRegistry.playC2S().register`.

### Senden eines Pakets an den Client {#sending-a-packet-to-the-client}

Um ein Paket mit unserem benutzerdefinierten Payload zu senden, können wir `ServerPlayNetworking.send` verwenden, das eine `ServerPlayerEntity` und einen `CustomPayload` entgegennimmt.

Beginnen wir mit der Erstellung unseres Blitz-Kartoffel-Item. Du kannst `use` überschreiben, um eine Aktion auszulösen, wenn das Item verwendet wird.
In diesem Fall, lasst uns Pakete an die Spieler in der Serverwelt senden.

@[code lang=java transcludeWith=:::lightning_tater_item](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Schauen wir uns den obigen Code an.

Wir senden nur Pakete, wenn die Aktion auf dem Server initiiert wird, indem wir frühzeitig mit einer `isClient`-Prüfung zurückkehren:

@[code lang=java transclude={22-24}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Wir erstellen eine Instanz des Payloads mit der Position des Users:

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

Schließlich rufen wir die Spieler in der Serverwelt durch `PlayerLookup` ab und senden ein Paket an jeden Spieler.

@[code lang=java transclude={28-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/LightningTaterItem.java)

:::info
Fabric API bietet `PlayerLookup`, eine Sammlung von Hilfsfunktionen, die Spieler auf einem Server suchen.

Ein häufig verwendeter Begriff zur Beschreibung der Funktionalität dieser Methoden ist "_Tracking_". Es bedeutet, dass eine Entität oder ein Chunk auf dem Server dem Client eines Spielers bekannt ist (innerhalb seiner Sichtweite) und die Entität oder die Block Entität sollte die
Clients über Änderungen informieren.

Tracking ist ein wichtiges Konzept für effizientes Networking, damit nur die notwendigen Spieler durch das Senden von Paketen über Änderungen informiert werden.
:::

### Empfangen eines Pakets auf dem Client {#receiving-a-packet-on-the-client}

Um ein von einem Server gesendetes Paket auf dem Client zu empfangen, musst du angeben, wie du das eingehende Paket behandeln willst.

Dies kann im **Client-Initialisierer** erfolgen, indem `ClientPlayNetworking.registerGlobalReceiver` aufgerufen und eine
`CustomPayload.Id` und ein `PlayPayloadHandler` übergeben wird, der eine funktionale Schnittstelle ist.

In diesem Fall definieren wir die auszulösende Aktion innerhalb der Implementierung der `PlayPayloadHandler`-Implementierung (als Lambda-Ausdruck).

@[code lang=java transcludeWith=:::client_global_receiver](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Schauen wir uns den obigen Code an.

Wir können auf die Daten aus unserem Payload zugreifen, indem wir die Getter-Methoden des Records aufrufen. In diesem Fall `payload.pos()`. Welche genutzt werden können, um die `x`,- `y`- und `z`-Positionen abzurufen.

@[code lang=java transclude={32-32}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Schließlich erstellen wir eine `LightningEntity` und fügen sie der Welt hinzu.

@[code lang=java transclude={33-38}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Wenn du nun diesen Mod auf einem Server installierst und ein Spieler dein Item "Lightning Tater" benutzt, sehen alle Spieler Blitze, die an der Position des Users einschlagen.

<VideoPlayer src="/assets/develop/networking/summon-lightning.webm" title="Summon lightning using Lightning Tater" />

### Senden eines Pakets an den Server {#sending-a-packet-to-the-server}

Genau wie beim Senden eines Pakets an den Client erstellen wir zunächst einen benutzerdefinierten Payload. Dieses Mal, wenn ein Spieler eine giftige Kartoffel auf einer lebenden Entität verwendet, fordern wir den Server auf, den Leuchten-Effekt auf diese anzuwenden.

@[code lang=java transcludeWith=:::give_glowing_effect_payload](@/reference/latest/src/main/java/com/example/docs/networking/basic/GiveGlowingEffectC2SPayload.java)

Wir übergeben den entsprechenden Codec zusammen mit einem Methodenverweis, um den Wert aus dem Record zu erhalten und diesen Codec zu bauen.

Dann registrieren wir unseren Payload in unserem **gemeinsamen Initialisierer**. Diesmal jedoch als _Client-to-Server_ Payload unter Verwendung von `PayloadTypeRegistry.playC2S().register`.

@[code lang=java transclude={26-26}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Um ein Paket zu senden, fügen wir eine Aktion hinzu, wenn der Spieler eine giftige Kartoffel benutzt. Wir werden das Event `UseEntityCallback` verwenden, um die Dinge übersichtlich zu halten.

Wir registrieren das Event in unserem **Client-Initialisierer**, und wir verwenden `isClient()`, um sicherzustellen, dass die Aktion nur auf dem logischen Client ausgelöst wird.

@[code lang=java transcludeWith=:::use_entity_callback](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Wir erstellen eine Instanz unseres `GiveGlowingEffectC2SPayload` mit den notwendigen Argumenten. In diesem Fall, der Netzwerk ID der Zielentität.

@[code lang=java transclude={51-51}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

Schließlich senden wir ein Paket an den Server, indem wir `ClientPlayNetworking.send` mit der Instanz unseres
`GiveGlowingEffectC2SPayload` aufrufen.

@[code lang=java transclude={52-52}](@/reference/latest/src/client/java/com/example/docs/network/basic/FabricDocsReferenceNetworkingBasicClient.java)

### Empfangen eines Pakets auf dem Server {#receiving-a-packet-on-the-server}

Dies kann im **gemeinsamen Initialisierer** geschehen, indem man `ServerPlayNetworking.registerGlobalReceiver` aufruft und eine `CustomPayload.Id` und einen `PlayPayloadHandler` übergibt.

@[code lang=java transcludeWith=:::server_global_receiver](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

:::info
Es ist wichtig, dass du den Inhalt des Pakets auf der Serverseite validierst.

In diesem Fall überprüfen wir anhand der Netzwerk-ID, ob die Entität existiert.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)

Außerdem muss es sich bei der Zielentität um eine lebende Entität handeln, und wir beschränken die Reichweite der Zielentität vom Spieler auf 5.

@[code lang=java transclude={32-32}](@/reference/latest/src/main/java/com/example/docs/networking/basic/FabricDocsReferenceNetworkingBasic.java)
:::

Wenn ein Spieler nun versucht, eine giftige Kartoffel auf einer lebenden Entität zu verwenden, wird der Leuchten-Effekt auf diese angewendet.

<VideoPlayer src="/assets/develop/networking/use-poisonous-potato.webm" title="Glowing effect is applied when a Poisonous Potato is used on a living entity." />
