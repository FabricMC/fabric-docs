---
title: Maak Je Eerste Blok
description: Leer hoe je je eerste zelfgemaakte blok kunt maken in Minecraft.
authors:
  - IMB11
  - xEobardThawne
  - its-miroma
---

# Maak Je Eerste Blok {#creating-your-first-block}

Blokken vormen de basis van Minecraft – letterlijk en figuurlijk. Net als alles in de game, worden ze netjes opgeborgen in registers.

## Je Blokken-klasse Voorbereiden {#preparing-your-blocks-class}

Als je de [Maak Je Eerste Voorwerp](../items/first-item) pagina hebt afgerond, zal dit proces erg vertrouwd aanvoelen - je zult een methode moeten maken die je blokken registreert, en het blokvoorwerp.

Je kunt deze methode het beste in een soort klasse genaamd `ModBlokken` (of wat je het ook wilt noemen) zetten.

Mojang doet iets soortgelijks als dit met vanilla blokken; je kunt de klasse `Blocks` raadplegen om te zien hoe zij het doen.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

Net als bij items moet je ervoor zorgen dat de klasse wordt geladen, zodat alle statische velden die je blokinstanties bevatten, worden geïnitialiseerd.

Je kunt dit doen door een dummy `initialize`-methode te maken, die in je mod-initialisator kan worden aangeroepen om de statische initialisatie te activeren.

:::info
Voor het geval dat je niet weet wat statische initialisatie is: dit is het proces van het initialiseren van statische velden in een klasse. Dit wordt gedaan wanneer de klasse door de JVM wordt geladen, en gebeurt voordat er exemplaren van de klasse worden gemaakt.
:::

```java
public class ModBlocks {
    // ...

    public static void initialize() {}
}
```

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/block/FabricDocsReferenceBlocks.java)

## Het Maken En Registreren Van Je Blok {#creating-and-registering-your-block}

Net als voorwerpen hebben blokken een klasse `Blocks.Settings` in hun constructor, die eigenschappen over het blok specificeert, zoals de geluidseffecten en het mining-niveau.

We zullen hier niet alle opties behandelen; je kunt de klasse zelf bekijken om de verschillende opties te zien, die voor zich spreken.

Ter voorbeeld zullen we een simpel blok maken dat de eigenschappen heeft van aarde, maar het is een ander materiaal.

:::tip
Je kunt ook `AbstractBlock.Settings.copy(AbstractBlock block)` gebruiken om de instellingen van een bestaand blok te kopiëren. In dit geval hadden we `Blocks.DIRT` kunnen gebruiken om de instellingen van aarde te kopiëren, maar voor het voorbeeld gebruiken we de bouwer.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Om het blokvoorwerp automatisch te maken, kunnen we `true` doorgeven aan de parameter `shouldRegisterItem` van de `register`-methode die we in de vorige stap hebben gemaakt.

### Je Blokvoorwerp Toevoegen aan een Voorwerpgroep {#adding-your-block-to-an-item-group}

Omdat 'BlockItem' automatisch wordt aangemaakt en geregistreerd, moet je, om het aan een voorwerpgroep toe te voegen, de methode 'Block.asItem()' gebruiken om de instantie 'BlockItem' op te halen.

Voor dit voorbeeld gebruiken we een aangepaste artikelgroep die is gemaakt op de pagina [Aangepaste Voorwerpgroepen](../items/custom-item-groups).

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

---

Je zou nu moeten merken dat jouw blok in de creatieve inventaris staat en in de wereld kan worden geplaatst!

![Block in world without suitable model or texture](/assets/develop/blocks/first_block_0.png).

Er zijn wel een paar problemen: het blokvoorwerp heeft geen naam en het blok heeft geen textuur, blokmodel of voorwerpmodel.

## Blokvertalingen toevoegen {#adding-block-translations}

Om een ​​vertaling toe te voegen, moet je een vertaalsleutel aanmaken in je vertaalbestand - `assets/<mod id here>/lang/nl_nl.json` (`us_en.json` voor Engels).

Minecraft zal deze vertaling gebruiken in de creatieve inventaris en op andere plaatsen waar de bloknaam wordt weergegeven, zoals bij commandofeedback.

```json
{
    "block.mod_id.condensed_dirt": "Condensed Dirt"
}
```

Je kunt het spel opnieuw starten of je mod bouwen en op <kbd>F3</kbd> + <kbd>T</kbd> drukken om de wijzigingen toe te passen - en je zou moeten zien dat het blok een naam heeft in de creatieve inventaris en op andere plaatsen zoals het statistiekenscherm.

## Modellen en Texturen {#models-and-textures}

Alle bloktexturen zijn te vinden in de map `assets/<mod id here>/textures/block` - een voorbeeldtextuur voor het blok "Condensed Dirt" is gratis te gebruiken.

<DownloadEntry type="Texture" visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png" />

Om de textuur in het spel te laten verschijnen, moet je een blok- en voorwerpmodel maken dat je kunt vinden op de respectievelijke locaties voor het blok "Condensed Dirt":

- `assets/<mod id here>/models/block/condensed_dirt.json`
- `assets/<mod id here>/models/item/condensed_dirt.json`

Het voorwerpmodel is vrij eenvoudig, het kan gewoon het blokmodel als ouder gebruiken - aangezien de meeste blokmodellen ondersteuning bieden voor weergave in een GUI:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/condensed_dirt.json)

Het blokmodel moet in ons geval echter als ouder het `block/cube_all`-model hebben:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/condensed_dirt.json)

Wanneer je het spel laadt, merk je misschien dat de textuur nog steeds ontbreekt. Dit komt omdat je een blockstaat-definitie moet toevoegen.

## Het Maken van De Blokstaat-definitie {#creating-the-block-state-definition}

De blokstaat-definitie wordt gebruikt om het spel te instrueren op basis van de huidige status van het blok.

Voor het voorbeeldblok, dat geen complexe blokstaat heeft, is slechts één invoer in de definitie nodig.

Dit bestand moet zich in de map `assets/mod_id/blockstates` bevinden en de naam ervan moet overeenkomen met het blok-ID dat is gebruikt bij het registreren van uw blok in de klasse `ModBlokken`. Als het blok-ID bijvoorbeeld `condensed_dirt` is, moet het bestand `condensed_dirt.json` heten.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/blockstates/condensed_dirt.json)

Blockstaten zijn erg complex en daarom worden ze op een volgende pagina behandeld: [Blockstaten](./blockstates)

Als je het spel opnieuw start, of herlaadt via <kbd>F3</kbd> + <kbd>T</kbd> om wijzigingen toe te passen, zou je de bloktextuur in de inventaris en fysiek in de wereld moeten kunnen zien:

![Block in world with suitable texture and model](/assets/develop/blocks/first_block_4.png)

## Blokdrops toevoegen {#adding-block-drops}

Wanneer je het blok breekt in overleefmodus, zie je misschien dat het blok niet valt. Misschien wil je deze functionaliteit, maar om je blok als een voorwerp te laten vallen, moet je de buittabel implementeren. Het buittabelbestand moet in de map `data/<0>/loot_table/blocks/` worden geplaatst.

:::info
Voor een beter begrip van buittabellen kun je de pagina [Minecraft Wiki - Buittabellen](https://nl.minecraft.wiki/w/Buittabel) raadplegen.
:::

@[code](@/reference/latest/src/main/resources/data/fabric-docs-reference/loot_tables/blocks/condensed_dirt.json)

Deze buittabel biedt een eenmalige voorwerpval van het blokvoorwerp wanneer het blok wordt gebroken en wanneer het wordt opgeblazen door een explosie.

## Een Oogstinstrument Voorstellen {#recommending-a-harvesting-tool}

Mogelijk wil je ook dat je blok alleen met een specifiek gereedschap kan worden geoogst. Misschien wilt je je blok bijvoorbeeld sneller laten oogsten met een schep.

Alle gereedschapstags moeten in de map `data/minecraft/tags/block/mineable/` worden geplaatst - waarbij de naam van het bestand afhangt van het type tool dat wordt gebruikt, een van de volgende:

- `hoe.json` (schoffel)
- `axe.json` (bijl)
- `pickaxe.json` (houweel)
- `shovel.json` (schep)

De inhoud van het bestand is vrij eenvoudig: het is een lijst met items die aan de tag moeten worden toegevoegd.

In dit voorbeeld wordt het blok "Condensed Dirt" toegevoegd aan de tag `shovel`.

@[code](@/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json)

Als je wilt dat er een tool nodig is om het blok te breken, kun je `.requiresTool()` toevoegen aan je blokinstellingen, en ook de juiste mining-tag toevoegen.

## Miningniveaus {#mining-levels}

Op dezelfde manier kan de tag voor miningniveau worden gevonden in de map `data/minecraft/tags/block/` en respecteert het volgende formaat:

- `needs_stone_tool.json` - Een minimumniveau aan stenen gereedschap
- `needs_iron_tool.json` - Een minimumniveau aan ijzeren gereedschap
- `needs_diamond_tool.json` - Een minimumniveau aan diamanten gereedschap.

Het bestand heeft hetzelfde formaat als het oogstinstrumentbestand: een lijst met items die aan de tag moeten worden toegevoegd.

## Extra Notities {#extra-notes}

Als je meerdere blokken aan je mod toevoegt, kunt je overwegen om [Data Generatie](https://fabricmc.net/wiki/tutorial:datagen_setup) te gebruiken om het proces van het maken van blok- en voorwerpmodellen, blokstaatdefinities en buittafels te automatiseren.
