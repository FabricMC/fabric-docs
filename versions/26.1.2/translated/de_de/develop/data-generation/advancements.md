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

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_provider_start

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_advancements_register

## Struktur eines Fortschritts {#advancement-structure}

Ein Fortschritt setzt sich aus mehreren Komponenten zusammen. Neben den Voraussetzungen, auch als "Kriterien" bezeichnet, kann er auch folgendes haben:

- Ein `DisplayInfo`, das dem Spiel mitteilt, wie der Fortschritt den Spielern angezeigt werden soll,
- `AdvancementRequirements`, bei denen es sich um Listen von Kriterien handelt, von denen mindestens ein Kriterium aus jeder Teilliste erfüllt sein muss,
- `AdvancementRewards`, die der Spieler für den Abschluss des Fortschritts erhält.
- Eine `Strategy`, die dem Fortschritt mitteilt wie er mehrere Kriterien verarbeiten soll, und
- Ein übergeordnetes `Advancement`, das die Hierachie organisiert, welche du in dem "Fortschritt" Fenster sehen kannst.

## Einfacher Fortschritt {#simple-advancements}

Hier ist ein einfacher Fortschritt, um einen Erdblock zu erhalten:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_simple_advancement

:::details JSON Ausgabe

<<< @/reference/26.1.2/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Übergeordnet {#parents}

Um einen Fortschrittsbaum anzulegen oder zu erweitern, können wir für unseren Fortschritt einen übergeordneten Eintrag festlegen. Rufe dazu `Advancement.Builder#parent(...)` auf und übergebe eine Referenz auf den übergeordneten Fortschritt.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference_parent

Wenn keine direkte Referenz auf den übergeordneten Fortschritt vorhanden ist (z. B. bei Verwendung eines Vanilla Fortschritt als übergeordneter Eintrag), kann mithilfe einer Bezeichnung ein Platzhalter angelegt werden.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder_parent

Deine Fortschritte sollten nun im Fortschrittsmenü als Baumstruktur angezeigt werden.

![Fortschrittsbaum](/assets/develop/data-generation/advancement_tree.png)

## Mehrere Kriterien {#multiple-criteria}

Um unsere Fortschritte mit komplexeren Bedingungen zu versehen, können wir `Advancement.Builder#addCriteria(...)` mehrmals mit zusätzlichen Kriterien aufrufen.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple_criteria

Im Standard müssen alle Kriterien erfüllt sein, damit der Fortschritt abgeschlossen werden kann. Wir können dieses Verhalten ändern, indem wir eine andere Strategie anwenden.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements_strategy

## Belohnungen {#rewards}

Wir können unseren Fortschritten Belohnungen zuweisen, die einem Spieler gewährt werden, sobald er den Fortschritt abgeschlossen hat. Dies erreichen wir, indem wir `Advancement.Builder#rewards(...)` mit den Belohnungen aufrufen, die wir hinzufügen möchten.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience_reward

Es gibt mehrere andere Belohnungsarten:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward_types

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

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_entrypoint

Beachte, dass dieser Code wirklich schlecht ist. Die `HashMap` wird nirgendwo dauerhaft gespeichert, daher wird sie bei jedem Neustart des Spiels zurückgesetzt. Es geht nur darum, `Criterion`s aufzuzeigen. Starte das Spiel und teste es!

Als Nächstes erstellen wir unser benutzerdefiniertes Kriterium `UseToolCriterion`. Es wird seine eigene Klasse `Conditions` benötigen, also werden wir beide auf einmal erstellen:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_base

Puh, das ist eine Menge! Schauen wir uns das mal genauer an.

- `UseToolCriterion` ist ein `SimpleCriterionTrigger`, auf das `Conditions` angewendet werden können.
- `Conditions` hat ein `playerPredicate` Feld. Alle `Conditions` sollten ein Spielerprädikat haben (technisch gesehen ein `LootContextPredicate`).
- `Conditions` haben auch einen `CODEC`. Dieser `Codec` ist einfach der Codec für sein einziges Feld, `playerPredicate`, mit zusätzlichen Anweisungen zur Konvertierung zwischen ihnen (`xmap`).

::: info

Um mehr über Codecs zu erfahren, sieh dir die [Codecs](../codecs) Seite an.

:::

Wir brauchen einen Weg, um zu überprüfen, ob Bedingungen erfüllt sind. Lasst uns eine Hilfsmethode zu `Conditions` hinzufügen:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_conditions_test

Da wir nun ein Kriterium und seine Bedingungen haben, brauchen wir eine Möglichkeit, es auszulösen. Füge eine Auslösungsmethode zu `UseToolCriterion` hinzu:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_trigger

Fast geschafft! Als nächstes benötigen wir eine Instanz unseres Kriteriums, mit der wir arbeiten können. Fügen wir sie in eine neue Klasse mit dem Namen `ModCriteria` ein.

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria

Um sicherzustellen, dass unsere Kriterien zum richtigen Zeitpunkt initialisiert werden, füge eine leere `init`-Methode hinzu:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria_init

Und rufe es in deinem Mod-Initialisierer auf:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_call_init

Schließlich müssen wir unser Kriterium auslösen. Füge dies zu der Stelle hinzu, an der wir in der Hauptmodklasse eine Nachricht an den Spieler geschickt haben.

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_criterion

Dein neues Kriterium ist jetzt einsatzbereit! Lasst es uns zu unserem provider hinzufügen:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_custom_criteria_advancement

Führe den Datengenerator Task erneut aus und du hast einen neuen Fortschritt bekommen, mit dem du spielen kannst!

## Bedingungen mit Parametern {#conditions-with-parameters}

Das ist alles schön und gut, aber was ist, wenn wir einen Fortschritt nur dann gewähren wollen, wenn wir etwas fünfmal getan haben? Und warum nicht noch einen bei zehn Mal? Hierfür müssen wir unserer Bedingung einen Parameter geben. Du kannst bei `UseToolCriterion` bleiben, oder du kannst mit einem neuen `ParameterizedUseToolCriterion` nachziehen. In der Praxis solltest du nur die parametrisierte Variante haben, aber für dieses Tutorial werden wir beide behalten.

Lass uns von unten nach oben arbeiten. Wir müssen prüfen, ob die Anforderungen erfüllt sind, also bearbeiten wir unsere Methode `Conditions#requirementsMet`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_requirements_met

`requiredTimes` existiert nicht, also mache es zu einem Parameter von `Conditions`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_parameter

Jetzt ist unser Codec fehlerhaft. Lass uns einen neuen Codec für die neuen Änderungen schreiben:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_codec

Nun müssen wir unsere `trigger`-Methode korrigieren:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_trigger

Wenn du ein neues Kriterium erstellt hast, müssen wir es zu `ModCriteria` hinzufügen

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_new_mod_criteria

Und rufe sie in unserer Hauptklasse auf, genau dort, wo die alte Klasse ist:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_new_criterion

Füge den Fortschritt zu deinem Provider hinzu:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_new_custom_criteria_advancement

Führe den Datengenerator erneut aus, und du bist endlich fertig!

## Ressourcenbedingungen {#resource-conditions}

Um eine [Ressourcenbedingung](../resource-conditions) auf einen datengenerierten Fortschritt anzuwenden, umschließe den Consumer mit `withConditions` und gebe die gewünschten Ressourcenbedingungen an. Dadurch wird ein Fortschritt generiert, auf den Ressourcenbedingungen angewendet werden:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_conditions
