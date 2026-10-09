---
title: Benutzerdefinierte Projektile
description: Lerne, wie man benutzerdefinierte Projektile hinzufügt.
authors:
  - ayutac
  - cassiancc
  - ChampionAsh5357
  - dicedpixels
  - Earthcomputer
  - ekulxam
  - haykam
  - kanpov
  - NetUserGet
  - onlyspxctre
  - patrickmsm
  - tianjun
  - upcraftlp
resources:
  https://minecraft.wiki/w/Projectile: Projektile - Minecraft Wiki
  https://docs.neoforged.net/docs/entities/#projectiles: Projektile - NeoForge Docs
---

Projektile sind Entitäten, die von Spielern oder anderen Entitäten geworfen oder abgefeuert werden können. In diesem Leitfaden werden wir uns die Implementierung eines einfachen Projektils wie beispielsweise eines Schneeballs anschauen.

Wir werden unser Projektil Hot Tater (Heiße Kartoffel) nennen. Es wird sich um eine Kartoffel handeln, die den Block oder die Entität, auf die sie trifft, in Brand setzt.

:::info VORAUSSETZUNGEN

Um ein Projektil zu erstellen, musst du sowohl ein Item als auch eine Entität registrieren. Wir empfehlen dir dafür, den Leitfaden [Erstellen deines ersten Items](../items/first-item) und [Erstellen deiner ersten Entität](./first-entity) durchzugehen.

:::

## Die Entität für das Projektil erstellen {#creating-the-projectile-entity}

Lasst uns eine `HotTaterEntity`, indem wir von `ThrowableItemProjectile` erben. Diese Klasse sollte sich in deinem `main` Quellensatz befinden.

Die Klasse `ThrowableItemProjectile` ist für die Physik und die Itemform des Projektils zuständig.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#entity

Hier ist ganz schön was los. Lasst uns die wichtigen Codeabschnitte einmal anschauen.

### Konstruktor {#constructors}

Wir definieren 3 Konstruktoren. Sie werden jeweils für die Registrierung von Entitäten, die Erzeugung von Projektilen und die Umwandlung von Projektilen verwendet.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#constructors

### Überschreiben von `getDefaultItem()` {#override-get-default-item}

Definiert die Itemform dieses Projektil.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#default_item

:::warning WICHTIG

Möglicherweise meldet deine IDE, dass sie das Item nicht auflösen kann: Wir werden es in Kürze im Abschnitt [Registrierung](#registration) erstellen.

:::

### Überschreiben von `onHitBlock()` {#override-on-hit-block}

Definiert das Verhalten, wenn das Projektil einen Block trifft. Wir prüfen, wo das Projektil getroffen hat, und setzen dann die Trefferfläche dieses Blocks in Brand. Diese Logik findet serverseitig statt.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_block

### Überschreiben von `onHitEntity()` {#override-on-hit-entity}

Definiert das Verhalten, wenn das Projektil eine Entität trifft. Wir setzen die Entität, die getroffen wurde, für 5 Sekunden in Brand.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_entity

### Überschreiben von `onHit()` {#override-on-hit}

Definiert, wie sich das Projektil verhält, wenn es auf etwas trifft, sei es ein Block oder eine Entität. Wir werden dies verwenden, um das Projektil zu verwerfen, sodass es bei einem Treffer entfernt wird; ohne dies würde das Projektil einfach weiterfliegen.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit

## Das Item erstellen {#creating-the-item}

Wir registrieren ein einfaches Item. Da wir die Logik zum Werfen implementieren müssen, wird unsere Klasse `HotTaterItem` von der Klasse `Item` erben und `ProjectileItem` implementieren. Diese Klasse sollte sich in deinem `main` Quellensatz befinden.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#item

Es ist eine Standard-Item-Implementierung mit einigen speziellen Methoden aus `ProjectileItem`. Lasst uns diese anschauen:

### Überschreiben von `asProjectile()` {#override-as-projectile}

Diese Methode wandelt das Item in seine Entitätsform um.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#as_projectile

### Überschreiben von `use()` {#override-use}

Definiert die Aktion, die ausgeführt wird, wenn das Item verwendet wird. In unserem Fall rufen wir die Hilfsmethode `Projectile.spawnProjectileFromRotation()` auf, um das Projektil zu erzeugen.

Zusätzlich zu den Standardparametern (Level, Item Stack und Spieler) nimmt diese Hilfsmethode drei weitere Float-Werte entgegen:

- `yOffset`: Versatz für die Neigung (Drehung um die X-Achse, nach oben oder unten), in Grad. Negative Werte richtigen die Anfangsgeschwindigkeit nach oben.
- `pow`: Multiplikator für die Geschwindigkeit der Projektilbewegung.
- `uncertainty`: Ungenauigkeit des Projektils. 0 bedeutet keine zufällige Streuung. Je höher dieser Wert ist, desto stärker streuen die Projektile, selbst wenn sie mit derselben Anfangsposition und Rotation geworfen werden.

Für weitere Informationen, siehe den [Minecraft Wiki Artikel zu Projektilen](https://minecraft.wiki/w/Projectile#Initial_conditions).

:::details Warum wird dieser Parameter `yOffset` genannt?

Das wissen wir auch nicht, lieber Leser. Trotz des Namens wird [der Versatz auf die Neigung angewendet](https://mcsrc.dev/2/26.2/net/minecraft/world/entity/projectile/Projectile#L156), welche die Rotation der _Geschwindigkeit_ um die X-Achse ist:

```java
float yd = -Mth.sin((xRot + yOffset) * (float) (Math.PI / 180.0));
```

Zum Beispiel, wenn Vanilla einen [`yOffset` von `-20.0F` für Wurftränke](https://mcsrc.dev/2/26.2/net/minecraft/world/item/ThrowablePotionItem#L28) verwendet, verändert dies die Neigung der initialen Geschwindigkeit von `source.getXRot() - 20.0F` (20 Grad nach oben, in Richtung Himmel).

Vielleicht wäre `xRotOffset` ein passenderer Name gewesen.

:::

Schließlich setzen wir den Status `ITEM_USED`, entnehmen ein Item vom Stack und markieren die Interaktion als erfolgreich.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#use

## Registrierung {#registration}

Registriere das Item, wie wir es in dem Leitfaden [Erstellen deines ersten Items](../items/first-item#registering-an-item) gemacht haben. Definierte zuerst den Schlüssel des Items in `ModItemIds`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#hot_tater

Registriere dann das Item in `ModItems`, zusammen mit den anderen:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#hot_tater

Vergiss nicht, ein [Modell](../items/first-item#adding-a-model), eine [Textur](../items/first-item#adding-a-texture) und ein [Client-Item](../items/first-item#creating-the-client-item) und einen [Namen](../items/first-item#naming-the-item) hinzuzufügen, unter Verwendung des Bezeichner `hot_tater`. Du solltest [das Item zu einem Kreativtab hinzufügen](../items/first-item#adding-the-item-to-a-creative-tab). Hier ist eine Beispiel Textur:

<DownloadEntry visualURL="/assets/develop/projectiles/hot_tater_preview.png" downloadURL="/assets/develop/projectiles/hot_tater.png">Textur</DownloadEntry>

Registriere auch die Entität, wie wir es in dem Leitfaden [Erstellen deiner ersten Entität](./first-entity#preparing-your-first-entity) getan haben, indem du als statisches Feld in `ModEntityTypes` hinzufügst. Da Entitäten und Items in getrennten Registrierungen gespeichert sind, verwendet der Entitätstyp einfach denselben Pfad wie das Item, wie beispielsweise bei `snowball` in Vanilla:

<<< @/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java#hot_tater

Lasst uns schließlich den Vanilla `ThrownItemRenderer` im Client-Initialisierer verwenden:

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#renderer

Und du bist fertig!

<VideoPlayer src="/assets/develop/projectiles/hot-tater.mp4">Eine Hot Tater (Heiße Kartoffel), die einen Dorfbewohner in Brand setzt</VideoPlayer>
