---
title: Mappings migrieren
description: Lerne, wie du die verschleierten Mappings deines Mods migrierst.
authors:
  - ArduFish
  - asiekierka
  - cassiancc
  - Daomephsta
  - deirn
  - Earthcomputer
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - liach
  - MildestToucan
  - modmuss50
  - modmuss50
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - basil4088
---

Möglicherweise möchtest du von Yarn zu Mojang Mappings migrieren, insbesondere wenn du planst, [deinen Mod nach dem Erscheinen des Gamedrop Mounts of Mayhem](#whats-going-on-with-mappings) zu aktualisieren, oder anders herum.

Es gibt zwei Möglichkeiten, dies zu erreichen: Du kannst entweder das **Loom Gradle Plugin** oder das **Ravel IntelliJ IDEA Plugin** verwenden.

Loom bietet eine halbautomatische Migration der Mappings über die Aufgabe `migrateMappings`, **unterstützt jedoch keine Migration von in Kotlin geschriebenem Code**.

Ravel ist ein Plugin für IntelliJ IDEA, das einen GUI-Dialog für die Migration hinzufügt. Im Gegensatz zu Loom unterstützt Ravel jedoch auch **Kotlin**. Darüber hinaus könnte Ravel bei komplexeren Projekten eine bessere Leistung als Loom erzielen, da es IDE zur Lösung von Änderungen verwendet.

::: info

Die Fabric-API verwendete Ravel, um von Yarn zu Mojang Mappings zu migrieren. Siehe [PR #4690 im Fabric API Repository](https://github.com/FabricMC/fabric/pull/4960).

:::

Keine der beiden Optionen ist perfekt, und du musst die Ergebnisse weiterhin überprüfen und manuelle Korrekturen vornehmen, insbesondere bei der Migration von Mixins.

<ChoiceComponent :choices="[
{
 name: 'Loom Gradle Plugin',
 href: './loom',
 icon: 'simple-icons:gradle',
 color: '#4DC9C0',
},
{
 name: 'Ravel IntelliJ IDEA Plugin',
 href: './ravel',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
]" />

## Was ist mit den Mappings los? {#whats-going-on-with-mappings}

In der Vergangenheit hat die Minecraft: Java Edition Verschleierung eingesetzt, was zur Entwicklung von Verschleierungskarten geführt hat, die Fabric Loom für Modding verwendet. Es gab zwei Möglichkeiten: entweder die Fabric-eigenen Yarn-Mappings oder die offiziellen Mojang-Mappings.

Mojang hat kürzlich angekündigt, dass [sie die Code-Verschleierung aus der Minecraft: Java Edition](https://www.minecraft.net/en-us/article/removing-obfuscation-in-java-edition) entfernen, woraufhin das Fabric-Projekt [mit seinen Plan zur Umsetzung dieser Änderung](https://fabricmc.net/2025/10/31/obfuscation.html) gefolgt ist. Wenn du vorhast, deinen Mod auf diese Version zu aktualisieren, musst du vor dem Update zunächst zu Mojangs Verschleierungsmappings wechseln.

## Was sind Mappings? {#mappings}

Die Minecraft: Java Edition wurde seit seiner Veröffentlichung verschleiert, was bedeutet, dass seine Codes mit menschenfreundlichen Klassennamen wie `Creeper` durch unverständliche Zeichenfolgen wie `brc` ersetzt wurden. Um das Modding zu vereinfachen, verwendet Fabric Loom Verschleierungskarten: Referenzen, die verschleierte Klassennamen wie `brc` wieder in menschenfreundliche Namen wie `CreeperEntity` übersetzen.

Als Fabric-Entwickler wirst du auf drei Hauptgruppen von Namen stoßen:

- **Intermediary**: Das von kompilierten Fabric-Mods verwendete Mapping-Set; beispielsweise kann `brc` zu `class_1548` werden. Der Sinn hinter Intermediary besteht darin, über verschiedene Versionen hinweg eine stabile Reihe von Namen anzubieten, da sich verschleierte Klassennamen mit jeder neuen Version von Minecraft ändern. Dadurch können Mods, die für eine bestimmte Version entwickelt wurden, oft auch auf anderen Versionen funktionieren, solange die betroffenen Teile des Spiels nicht zu stark verändert wurden.
- **Yarn**: Ein von Fabric entwickeltes Open-
  Source-Mapping-Set, mit dem Menschen Mods schreiben können. Die meisten Fabric-Mods verwendeten Yarn Mappings, da diese vor 2025 der Standard waren. Ein Beispiel für eine Zuordnung könnte `CreeperEntity` sein.
- **Mojang Mappings**: Die offiziellen Verschleierungsmappings des Spiels, die 2019 von Mojang veröffentlicht wurden, um die Mod-Entwicklung zu unterstützen. Insbesondere fehlen in den Verschleierungsmappings von Mojang Parameternamen und Javadocs, weshalb einige Benutzer zusätzlich [Parchment](https://parchmentmc.org/) über die offiziellen Mappings legen. Ein Beispiel für ein Mapping könnte `Creeper` sein.

Minecraft 26.1 wird nicht verschleiert sein und enthält Parameternamen, sodass keine Mappings erforderlich sind.
