---
title: Generierung von Übersetzungen
description: Ein Leitfaden zur Einrichtung der Generierung von Übersetzungen mit dem Datengenerator.
authors:
  - CelDaemon
  - IMB11
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
  - sjk1949
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung der Datengenerierung](./setup) zuerst abgeschlossen hast.

:::

## Einrichtung {#setup}

Zuerst werden wir unseren **Provider** vorbereiten. Denke daran, dass es die Provider sind, die die Daten für uns generieren. Erstelle eine Klasse, die von `FabricLanguageProvider` erbt und fülle die Basismethoden aus:

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: tip

Du wirst für jede Sprache, die du generieren möchtest, einen eigenen Provider benötigen (z. B. einen `ExampleEnglishLangProvider` und einen `ExamplePirateLangProvider`).

:::

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

@[code lang=java transcludeWith=:::datagen-translations:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Erstellen von Übersetzungen {#creating-translations}

Neben der Erstellung von Rohübersetzungen, Übersetzungen aus einem `Identifier` und dem Kopieren aus einer bereits existierenden Datei (durch die Übergabe eines `Path`), gibt es Hilfsmethoden für die Übersetzung von Items, Blöcken, Tags, Statistiken, Entitäten, Statuseffekten, Kreativtabs, Entitätsattributen und Verzauberungen. Rufe einfach `add` beim `translationBuilder` auf und gebe an, was du übersetzen willst und in zu was es übersetzt werden soll:

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

## Nutzen von Übersetzungen {#using-translations}

Generierte Übersetzungen ersetzen viele Übersetzungen, die in anderen Tutorials hinzugefügt wurden, aber du kannst sie auch überall dort verwenden, wo du ein `Component`-Objekt verwendest. In unserem Beispiel, wenn wir Ressourcenpaketen erlauben wollen unsere Begrüßung zu übersetzen, nutzen wir `Component.translatable` anstelle von `Component.literal`:

```java
ChatComponent chatHud = Minecraft.getInstance().gui.getChat();
chatHud.addMessage(Component.literal("Hello there!")); // [!code --]
chatHud.addMessage(Component.translatable("text.example-mod.greeting")); // [!code ++]
```
