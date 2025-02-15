---
title: Blok Staten
description: Leer waarom blok staten een goede manier zijn om visuele functies toe te voegen aan je blokken.
authors:
  - IMB11
---

Een block staat is een stukje data gekoppeld aan een enkel blok in de Minecraft wereld, wat informatie bevat over het blok in de vorm van eigenschappen - hier een paar voorbeelden van eigenschappen die vanilla opslaat:

- Rotatie: Het meest gebruikt voor boomstammen en andere natuur gerelateerde blokken.
- Geactiveerd: Erg sterk gebruikt in vele Redstone apparaten, en blokken zoals de Oven of de Roker.
- Leeftijd: Gebruikt voor gewassen, planten, kiemplanten, etc.

Je kunt je dus wel inbeelden waarom ze zo handig zijn - ze vermijden de noodzaak om gegevens in een blok entiteit op te slaan - wat de wereld grootte verkleint en TPS-problemen voorkomt!

Blok staat definities kunnen gevonden worden in de `assets/<mod id here>/blockstates` folder.

## Voorbeeld: Pilaar Blok {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft heeft al een paar zelfgemaakte classen die je gemakkelijk bepaalde types blok laat maken - dit voorbeeld gaat over het maken van een blok met de `axis` (as) eigenschap door een "Gecondenseerde Eiken Boomstam" blok te maken.

Met de vanilla class `PillarBlock` kan het blok in de X-, Y- of Z-as worden geplaatst.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Pilaren hebben twee texturen, de bovenkant en de zijkant - ze maken gebruik van het `block/cube_collumn` model.

Zoals gewoonlijk, met alle blok texturen, kunnen de textuurbestanden worden gevonden in `assets/<mod id here>/textures/block`

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip" />

Omdat de pilaar twee posities heeft, horizontaal en verticaal, zullen we twee verschillende modelbestanden moeten maken:

- `condensed_oak_log_horizontal.json` wat het `block/cube_column_horizontal` model uitbreidt.
- `condensed_oak_log.json` wat het `block/cube_column` model uitbreidt.

Hier een voorbeeld van het bestand `condensed_oak_log_horizontal.json`:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_oak_log_horizontal.json)

---

::: info
Remember, blockstate files can be found in the `assets/<mod id here>/blockstates` folder, the name of the blockstate file should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_oak_log`, the file should be named `condensed_oak_log.json`.

Voor een meer diepgaande blik op alle modificaties die beschikbaar zijn in de blockstate-bestanden, ga naar de pagina [Minecraft Wiki - Models (Block States)](https://minecraft.wiki/w/Tutorials/Models#Block_states).
:::

Vervolgens zullen we een blok staat bestand moeten maken. Het blok staat bestand is waar de magie gebeurt: pilaren hebben drie assen, dus we zullen specifieke modellen gebruiken voor de volgende situaties:

- `axis=x` - Wanneer het blok langs de X-as wordt geplaatst, roteren we het model in de positieve X-richting.
- `axis=y` - Wanneer het blok langs de Y-as wordt geplaatst, gebruiken we het normale verticale model.
- `axis=z` - Wanneer het blok langs de Z-as wordt geplaatst, roteren we het model in de positieve X-richting.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_oak_log.json)

Zoals altijd moet je een vertaling voor uw blok maken, en een voorwerp model dat de "parent" is van een van de twee modellen.

![Example of Pillar block in-game](/assets/develop/blocks/blockstates_1.png)

## Aangepaste blok staten {#custom-block-states}

Aangepaste blok staten zijn geweldig als je blok unieke eigenschappen heeft - soms zul je ook zien dat je blok vanilla eigenschappen kan hergebruiken.

Dit voorbeeld creëert een unieke boolean eigenschap met de naam `activated` - wanneer een speler met de rechtermuisknop op het blok klikt, gaat het blok van `activated=false` naar `activated=true` - waardoor de textuur net zo wordt gewijzigd.

### Het maken van De Eigenschap {#creating-the-property}

Ten eerste moet je de eigenschap zelf maken - sinds dit een boolean is, zullen we de `BooleanProperty.of` methode gebruiken.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Vervolgens moeten we de eigenschap toevoegen aan de blok staat manager in de methode `appendProperties`. Je zult de methode moeten overschrijven om toegang te krijgen tot de builder:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

Je moet ook een standaardstaat instellen voor de eigenschap `activated` in de constructor van jouw aangepaste blok.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

:::warning
Vergeet niet om je blok te registreren met de zelfgemaakte class in plaats val `Block`!
:::

### Het Gebruik van De Eigenschap {#using-the-property}

In dit voorbeeld wordt de boolean eigenschap `activated` omgedraaid wanneer de speler interactie heeft met het blok. We kunnen hiervoor de `onUse` methode overschrijven:

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

### Het Visualiseren van De Eigenschap {#visualizing-the-property}

Voordat je het blok staat bestand maakt, moet je texturen opgeven voor zowel de geactiveerde als de gedeactiveerde staten van het blok, evenals voor het blokmodel.

<DownloadEntry type="Textures" visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip" />

Gebruik je kennis van blokmodellen om twee modellen voor het blok te maken: één voor de geactiveerde staat en één voor de gedeactiveerde staat. Zodra je dat gedaan hebt, kun je het blok staat bestand gaan maken.

Omdat je een nieuwe eigenschap hebt gemaakt, moet je het blok staat bestand voor het blok bijwerken om rekening te houden met die eigenschap.

Als je meerdere eigenschappen hebt voor een blok, moet je met alle mogelijke combinaties rekening houden. `activated` en `axis` zouden bijvoorbeeld leiden tot 6 combinaties (twee mogelijke waarden voor `activated` en drie mogelijke waarden voor `axis`).

Omdat dit blok maar twee mogelijke varianten heeft, omdat er maar een eigenschap is (`activated`), zal de blok staat JSON er ongeveer zo uitzien:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/prismarine_lamp.json)

---

Omdat het voorbeeldblok een lamp is, zullen we er ook voor moeten zorgen dat het licht uitstraalt als de eigenschap `activated` waar is. Dit kan worden gedaan via de blokinstellingen die aan de constructor worden doorgegeven bij het registreren van het blok.

U kunt de `luminance`-methode gebruiken om het lichtniveau in te stellen dat door het blok wordt uitgezonden. We kunnen een statische methode maken in de `PrismarineLampBlock` class om het lichtniveau terug te geven op basis van de `activated`-eigenschap, en dit doorgeven als een methodereferentie naar de `luminance`-methode:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java)

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

Zodra je klaar bent met alles, zal het resultaat er ongeveer zo uitzien:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm" title="Prismarine Lamp Block in-game" />
