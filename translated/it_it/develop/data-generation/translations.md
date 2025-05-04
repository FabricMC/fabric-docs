---
title: Generazione di Traduzioni
description: Una guida per configurare la generazione di traduzioni con datagen.
authors:
  - IMB11
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
  - sjk1949
---

:::info PREREQUISITI
Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).
:::

## Configurazione {#setup}

Anzitutto, creeremo il nostro **fornitore**. Ricorda: i fornitori sono ciò che ci genera effettivamente i dati. Crea una classe che `extends FabricLanguageProvider` e compilane i metodi di base:

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

:::tip
Ti servirà un fornitore diverso per ogni lingua che vorrai generare (per esempio un `ExampleEnglishLangProvider` e un `ExamplePirateLangProvider`).
:::

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

@[code lang=java transclude={28-28}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Creare Traduzioni {#creating-translations}

Oltre a creare traduzioni crude, traduzioni da `Identifier`, e copiarli da un altro file esistente (passando un `Path`), ci sono metodi ausiliari per tradurre oggetti, blocchi, tag, statistiche, entità, effetti di stato, gruppi di oggetti, attributi di entità, e incantesimi. Basta chiamare `add` sul `translationBuilder` con ciò che vuoi tradurre e ciò a cui dovrebbe essere tradotto:

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

## Usare le Traduzioni {#using-translations}

Le traduzioni generate prendono il posto di molte traduzioni aggiunte in altri tutorial, ma puoi anche usarle in ogni punto in cui usi un oggetto `Text`. Nel nostro esempio, se volessimo permettere ai pacchetti risorse di tradurre il nostro saluto, usiamo `Text.translatable` invece di `Text.of`:

```java
ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
chatHud.addMessage(Text.literal("Hello there!")); // [!code --]
chatHud.addMessage(Text.translatable("text.fabric_docs_reference.greeting")); // [!code ++]
```
