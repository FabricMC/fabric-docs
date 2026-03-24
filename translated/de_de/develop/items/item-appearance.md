---
title: Aussehen des Items
description: Lerne, wie du Items dynamisch mit benutzerdefinierten Farbquellen einfärben kannst.
authors:
  - dicedpixels
---

Du kannst das Aussehen eines Items über sein Client Item manipulieren. Es gibt eine [Liste an Vanilla Modifikatoren im Minecraft Wiki](https://minecraft.wiki/w/Items_model_definition#Items_model_types).

Ein häufig verwendeter Typ davon sind _Farbquellen_, die dir erlauben die Farbe eines Items basierend auf vordefinierten Bedingungen ändern zu können.

Es gibt nur eine Handvoll [vordefinierter Farbquellen](https://minecraft.wiki/w/Items_model_definition#Tint_sources_types), also schauen wir uns an, wie wir unsere eigenen erstellen können.

Für dieses Beispiel registrieren wir ein Item. Wenn du mit diesem Vorgang nicht vertraut bist, lies bitte zuerst die Informationen zur [Registrierung eines Items](./first-item).

@[code lang=java transcludeWith=:::item](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Stelle sicher, folgendes hinzuzufügen:

- Ein [Client Item](./first-item#creating-the-client-item) in `/items/waxcap.json`
- Ein [Item Modell](./item-models) in `/models/item/waxcap.json`
- Eine [Textur](./first-item#adding-a-texture) in `/textures/item/waxcap.png`

Dass Item sollte im Spiel erscheinen.

![Registriertes Item](/assets/develop/item-appearance/item_tint_0.png)

Wie du sehen kannst, verwenden wir eine Graustufen-Textur. Fügen wir mit einer Farbquelle etwas Farbe hinzu.

## Item Farbquellen {#item-tint-sources}

Registrieren wir eine benutzerdefinierte Farbquelle, um unser Waxcap-Item einzufärben, sodass es bei Regen blau und ansonsten braun erscheint.

Zunächst müssen Sie eine benutzerdefinierte Farbquelle für das Item definieren. Dies geschieht durch die Implementierung des Interface `ItemTintSource` in einer Klasse oder einem Record.

@[code lang=java transcludeWith=:::tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Da dies Teil der Client Item Definition ist, können die Farbwerte mit einem Ressourcenpaket geändert werden. Du musst also einen [Map Codec](../codecs#mapcodec) definieren, der deine Farbton-Definition lesen kann. In diesem Fall hat die Farbquelle einen `int`-Wert, der die Farbe beschreibt, die sie bei Regen haben wird. Wir können den integrierten `ExtraCodecs.RGB_COLOR_CODEC` verwenden, um unseren Codec zusammenzustellen.

@[code lang=java transclude={17-20}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Wir können diesen Codec dann in `type()` zurückgeben.

@[code lang=java transclude={35-38}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Schließlich können wir eine Implementierung für `calculate` bereitstellen, die entscheidet, wie der Farbton aussehen soll. Der Wert von `color` stammt aus dem Ressourcenpaket.

@[code lang=java transclude={26-33}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Anschließend müssen wir unsere Item Farbquelle registrieren. Dies geschieht im **Client Initialisierer** unter Verwendung des in `ItemTintSources` deklarierten `ID_MAPPER`.

@[code lang=java transcludeWith=:::item_tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Sobald dies erledigt ist, können wir unsere Item Farbquelle in einer Client Item Definition verwenden.

@[code lang=json transclude](@/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json)

Du kannst die Farbänderung des Items im Spiel wahrnehmen.

![Item Färbung im Spiel](/assets/develop/item-appearance/item_tint_1.png)
