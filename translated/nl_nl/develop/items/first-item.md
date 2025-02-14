---
title: Maak Je Eerste Voorwerp
description: Leer hoe je een simpel voorwerp registreert en hoe je textureert, modelleert en een benoemt.
authors:
  - IMB11
  - dicedpixels
---

# Maak Je Eerste Voorwerp {#creating-your-first-item}

Op deze pagina maak je kennis met enkele belangrijke concepten met betrekking tot voorwerpen, en hoe je ze kunt registreren, texturen, modelleren en benoemen.

Als je het nog niet wist: alles in Minecraft wordt opgeslagen in registers, en voorwerpen vormen daarop geen uitzondering.

## Je Voorwerpen-klasse Voorbereiden {#preparing-your-items-class}

Om de registratie van items te vereenvoudigen, kunt u een methode maken die een exemplaar van een item en een string-ID accepteert.

Met deze methode wordt een item gemaakt met de opgegeven identificatie en wordt dit geregistreerd bij het voorwerpregister van het spel.

Je kunt deze methode het beste in een soort klasse genaamd `ModVoorwerpen` (of wat je het ook wilt noemen) zetten.

Mojang doet dit ook voor hun voorwerpen! Neem maar eens een kijkje naar de `Items` klasse voor inspiratie.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Het Registreren van een Voorwerp {#registering-an-item}

Je kunt nu een voorwerp registreren met de methode.

De voorwerpconstructor neemt een exemplaar van de `Items.Settings` klasse als een parameter. Met deze klasse kun je de eigenschappen van het voorwerp configureren via verschillende bouwermethodes.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Items.Settings`/`FabricItemSettings` class.

Dit werkt niet als je het item als beschadigbaar hebt gemarkeerd, omdat de stapelgrootte voor beschadigbare items altijd 1 is om duplicatie-exploits te voorkomen.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Als je echter in het spel gaat, kun je zien dat ons item niet bestaat! Dit komt omdat u de klasse niet statisch initialiseert.

Om dit te doen, kunt u een openbare statische initialisatiemethode aan uw klasse toevoegen en deze vanuit uw klasse `ModInitializer` aanroepen. Momenteel heeft deze methode niets nodig.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Het aanroepen van een methode voor een klasse initialiseert deze statisch als deze nog niet eerder is geladen - dit betekent dat alle `static` velden worden geëvalueerd. Dit is waar de dummy `initialize` voor is.

## Het Voorwerp Toevoegen aan een Voorwerpgroep {#adding-the-item-to-an-item-group}

:::info
Als je het item wilt toevoegen aan een aangepaste `ItemGroup`, ga dan naar de pagina [Aangepaste Voorwerpgroepen](./custom-item-groups) voor meer informatie.
:::

We zullen dit item bijvoorbeeld toevoegen aan de ingrediënten `ItemGroup`, u zult de voorwerpgroepgebeurtenissen van Fabric API moeten gebruiken - met name `ItemGroupEvents.modifyEntriesEvent`

Dit kan gedaan worden in de `initialize`-methode van uw voorwerpen klasse.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Als je het spel inlaadt, kun je zien dat ons item is geregistreerd en zich in de voorwerpgroep Ingrediënten bevindt:

![Item in the ingredients group](/assets/develop/items/first_item_0.png)

Echter mist het het volgende:

- Voorwerpmodel
- Textuur
- Vertaling (Naam)

## Het Voorwerp Benoemen {#naming-the-item}

Momenteel heeft het voorwerp geen vertaling, dus je zult er een moeten toevoegen. De vertaalsleutel is al door Minecraft verstrekt: `item.mod_id.suspicious_substance`.

Maak een nieuw JSON-bestand in: `src/main/resources/assets/<mod id here>/lang/nl_nl.json` (`en_us.json` voor Engels) en plaats de vertaalsleutel en de waarde ervan:

```json
{
    "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

Je kunt het spel opnieuw starten of je mod bouwen en op <kbd>F3</kbd>+<kbd>T</kbd> drukken om de wijzigingen toe te passen.

## Een Textuur en Model Toevoegen {#adding-a-texture-and-model}

Om je voorwerp een ​​textuur en model te geven, maak je eenvoudig een textuurafbeelding van 16x16 pixels voor uw item en slaat u deze op in de map `assets/<mod id here>/textures/item`. Geef het textuurbestand dezelfde naam als de ID van het voorwerp, maar met de extensie `.png`.

Je kunt deze voorbeeldtextuur bijvoorbeeld gebruiken voor `suspicious_substance.png`

<DownloadEntry type="Texture" visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png" />

Bij het herstarten/herladen van het spel zou je moeten zien dat het voorwerp nog steeds geen textuur heeft, dat komt omdat je een model moet toevoegen dat deze textuur gebruikt.

Je gaat een eenvoudig `item/generated`-model maken, dat een invoertextuur bevat en niets anders.

Maak de model-JSON in de map `assets/<mod id here>/models/item`, met dezelfde naam als het item; `suspicious_substance.json`

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### Het Model-JSON Afbreken {#breaking-down-the-model-json}

- `parent`: Dit is het ouder model wat dit model van zal erven. In dit geval is het het `item/generated`-model.
- `textures`: Dit is waar je de texturen voor het model definieert. De `layer0`-sleutel is de textuur wat het voorwerpmodel zal gebruiken.

De meeste voorwerpen zullen `item/generated` als ouder hebben, omdat het een eenvoudig model is wat alleen maar de textuur weergeeft.

Er zijn alternatieven, zoals `item/handheld`, wat gebruikt wordt voor voorwerpen die vastgehouden worden in de spelers handen, zoals gereedschappen.

Je voorwerp moet er nu zo uitzien in het spel:

![Item with correct model](/assets/develop/items/first_item_2.png)

## Het Voorwerp Composteerbaar of een Brandstof Maken {#making-the-item-compostable-or-a-fuel}

Fabric API biedt verschillende registers die kunnen worden gebruikt om extra eigenschappen aan je voorwerp toe te voegen.

Als je je voorwerp bijvoorbeeld composteerbaar wilt maken, kun je het `CompostableItemRegistry` gebruiken:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Als je van je voorwerp een ​​brandstof wilt maken, kun je ook de klasse `FuelRegistry` gebruiken:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Een Basisrecept Toevoegen {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Als u een recept voor uw item wilt toevoegen, moet u een JSON-receptbestand in de map `data/<mod id here>/recipe` plaatsen.

Voor meer informatie over het receptformaat kunt u deze bronnen raadplegen:

- [Recept JSON Generator](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - Recipe JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Aangepaste Tooltips {#custom-tooltips}

Als je wilt dat je voorwerp een ​​aangepaste tooltip heeft, moet je een klasse maken die `Item` uitbreidt en de methode `appendTooltip` overschrijft.

:::info
In dit voorbeeld wordt de klasse `LightningStick` gebruikt die is gemaakt op de pagina [Aangepaste Voorwerpinteracties](./custom-item-interactions).
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Elke aanroep van `add()` zal één regel aan de tooltip toevoegen.

![Tooltip Showcase](/assets/develop/items/first_item_3.png)
