---
title: Generierung von Fortschritten
description: Ein Leitfaden zur Einrichtung der Generierung von Fortschritten mit dem Datengenerator.
authors:
  - CelDaemon
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

## Einrichtung {#setup}

Zuerst müssen wir unseren Provider erstellen. Erstelle eine Klasse, die von `FabricAdvancementProvider` erbt und fülle die Basismethoden aus:

@[code lang=java transcludeWith=:::datagen-advancements:provider-start](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

@[code lang=java transcludeWith=:::datagen-advancements:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Struktur eines Fortschritts {#advancement-structure}

Ein Fortschritt setzt sich aus mehreren Komponenten zusammen. Neben den Voraussetzungen, auch als "Kriterien" bezeichnet, kann er auch folgendes haben:

- Ein `DisplayInfo`, das dem Spiel mitteilt, wie der Fortschritt den Spielern angezeigt werden soll,
- `AdvancementRequirements`, bei denen es sich um Listen von Kriterien handelt, von denen mindestens ein Kriterium aus jeder Teilliste erfüllt sein muss,
- `AdvancementRewards`, die der Spieler für den Abschluss des Fortschritts erhält.
- Eine `Strategy`, die dem Fortschritt mitteilt wie er mehrere Kriterien verarbeiten soll, und
- Ein übergeordnetes `Advancement`, das die Hierachie organisiert, welche du in dem "Fortschritt" Fenster sehen kannst.

## Einfacher Fortschritt {#simple-advancements}

Hier ist ein einfacher Fortschritt, um einen Erdblock zu erhalten:

@[code lang=java transcludeWith=:::datagen-advancements:simple-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

::: warning

Denke bei der Erstellung deiner Einträge für Fortschritte daran, dass die Funktion den `Identifier` des Fortschritts im Format `String` annimmt!

:::

:::details JSON Ausgabe

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json)

:::

## Ein weiteres Beispiel {#one-more-example}

Um den Dreh raus zu bekommen, fügen wir noch einen weiteren Fortschritt hinzu. Wir üben das Hinzufügen von Belohnungen, die Verwendung mehrerer Kriterien und die Zuweisung von Eltern:

@[code lang=java transcludeWith=:::datagen-advancements:second-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

## Benutzdefinierte Kriterien {#custom-criteria}

::: warning

Während der Datengenerator auf der Client-Seite liegen kann, befinden sich `Criterion`s und `Predicate`s im Hauptquellenverzeichnis (beide Seiten), da der Server sie auslösen und auswerten muss.

:::

### Definitionen {#definitions}

Ein **Kriterium** (Plural: Kriterien) ist etwas, was Spieler machen können (oder was einem Spieler passieren kann) was möglicherweise einem Fortschritt angerechnet wird. Das Spiel kommt mit vielen [Kriterien](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), welche in dem `net.minecraft.advancements.criterion` Packet gefunden werden können. Generell musst du nur ein neues Kriterium hinzufügen, wenn du eine benutzdefinierte Mechanik zum Spiel hinzufügst.

**Bedingungen** werden von Kriterien ausgewertet. Ein Kriterium wird nur gezählt, wenn alle relevanten Bedingungen zutreffen. Bedingungen werden in der Regel durch ein Prädikat ausgedrückt.

Ein **Prädikat** ist etwas, das einen Wert entgegennimmt und einen `boolean` zurück gibt. Zum Beispiel, ein `Predicate<Item>` gibt möglicherweise `true` zurück, wenn das Item ein Diamant ist, während ein `Predicate<LivingEntity>` möglicherweise `true` zurückgibt, wenn die Entität nicht gegenüber einem Dorfbewohner feindlich gesinnt ist.

### Erstellen von benutzdefinierten Kriterien {#creating-custom-criteria}

Zuerst müssen wir eine neue Mechanik implementieren. Wir können dem Spieler jedes Mal, wenn er einen Block abbaut, mitteilen, welches Werkzeug er benutzt hat.

@[code lang=java transcludeWith=:::datagen-advancements:entrypoint](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Beachte, dass dieser Code wirklich schlecht ist. Die `HashMap` wird nirgendwo dauerhaft gespeichert, daher wird sie bei jedem Neustart des Spiels zurückgesetzt. Es geht nur darum, `Criterion`s aufzuzeigen. Starte das Spiel und teste es!

Als Nächstes erstellen wir unser benutzerdefiniertes Kriterium `UseToolCriterion`. Es wird seine eigene Klasse `Conditions` benötigen, also werden wir beide auf einmal erstellen:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Puh, das ist eine Menge! Schauen wir uns das mal genauer an.

- `UseToolCriterion` ist ein `SimpleCriterionTrigger`, auf das `Conditions` angewendet werden können.
- `Conditions` hat ein `playerPredicate` Feld. Alle `Conditions` sollten ein Spielerprädikat haben (technisch gesehen ein `LootContextPredicate`).
- `Conditions` haben auch einen `CODEC`. Dieser `Codec` ist einfach der Codec für sein einziges Feld, `playerPredicate`, mit zusätzlichen Anweisungen zur Konvertierung zwischen ihnen (`xmap`).

::: info

Um mehr über Codecs zu erfahren, sieh dir die [Codecs](../codecs) Seite an.

:::

Wir brauchen einen Weg, um zu überprüfen, ob Bedingungen erfüllt sind. Lasst uns eine Hilfsmethode zu `Conditions` hinzufügen:

@[code lang=java transcludeWith=:::datagen-advancements:conditions-test](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Da wir nun ein Kriterium und seine Bedingungen haben, brauchen wir eine Möglichkeit, es auszulösen. Füge eine Auslösungsmethode zu `UseToolCriterion` hinzu:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Fast geschafft! Als nächstes benötigen wir eine Instanz unseres Kriteriums, mit der wir arbeiten können. Fügen wir sie in eine neue Klasse mit dem Namen `ModCriteria` ein.

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Um sicherzustellen, dass unsere Kriterien zum richtigen Zeitpunkt initialisiert werden, füge eine leere `init`-Methode hinzu:

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria-init](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Und rufe es in deinem Mod-Initialisierer auf:

@[code lang=java transcludeWith=:::datagen-advancements:call-init](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Schließlich müssen wir unsere Kriterien auslösen. Füge dies zu der Stelle hinzu, an der wir in der Hauptmodklasse eine Nachricht an den Spieler geschickt haben.

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Dein neues Kriterium ist jetzt einsatzbereit! Lasst es uns zu unserem provider hinzufügen:

@[code lang=java transcludeWith=:::datagen-advancements:custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Führe den Datengenerator Task erneut aus und du hast einen neuen Fortschritt bekommen, mit dem du spielen kannst!

## Bedingungen mit Parametern {#conditions-with-parameters}

Das ist alles schön und gut, aber was ist, wenn wir einen Fortschritt nur dann gewähren wollen, wenn wir etwas fünfmal getan haben? Und warum nicht noch einen bei zehn Mal? Hierfür müssen wir unserer Bedingung einen Parameter geben. Du kannst bei `UseToolCriterion` bleiben, oder du kannst mit einem neuen `ParameterizedUseToolCriterion` nachziehen. In der Praxis solltest du nur die parametrisierte Variante haben, aber für dieses Tutorial werden wir beide behalten.

Lass uns von unten nach oben arbeiten. Wir müssen prüfen, ob die Anforderungen erfüllt sind, also bearbeiten wir unsere Methode `Conditions#requirementsMet`:

@[code lang=java transcludeWith=:::datagen-advancements:new-requirements-met](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

`requiredTimes` existiert nicht, also mache es zu einem Parameter von `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:new-parameter](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Jetzt ist unser Codec fehlerhaft. Lass uns einen neuen Codec für die neuen Änderungen schreiben:

@[code lang=java transcludeWith=:::datagen-advancements:new-codec](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Nun müssen wir unsere `trigger`-Methode korrigieren:

@[code lang=java transcludeWith=:::datagen-advancements:new-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Wenn du ein neues Kriterium erstellt hast, müssen wir es zu `ModCriteria` hinzufügen

@[code lang=java transcludeWith=:::datagen-advancements:new-mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Und rufe sie in unserer Hauptklasse auf, genau dort, wo die alte Klasse ist:

@[code lang=java transcludeWith=:::datagen-advancements:trigger-new-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Füge den Fortschritt zu deinem Provider hinzu:

@[code lang=java transcludeWith=:::datagen-advancements:new-custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Führe den Datengenerator erneut aus, und du bist endlich fertig!
