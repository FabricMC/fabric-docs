---
title: Änderungen an der Beutetabelle
description: Ein Leitfaden zur Bearbeitung von Beutetabellen mithilfe von Events, die von der Fabric API bereitgestellt werden.
authors:
  - NotNightSky
  - its-miroma
  - cassiancc
---

Das Beutetabellen-System legt fest, welche Items fallen gelassen werden, wenn ein Block zerstört, eine Entität getötet oder eine Truhe geöffnet wird. Die Fabric API bietet dir verschiedene Möglichkeiten, Beutetabellen während des Ladevorgangs zu ändern, zu ersetzen und anzupassen sowie die endgültigen Drops zur Laufzeit anzupassen.

Die Fabric Loot API stellt über die Klasse `LootTableEvents` verschiedene Events zur Verfügung:

- [`LootTableEvents.MODIFY`](#modifying-loot-tables): Behalte die ursprüngliche Beutetabelle und füge Pools und Einträge hinzu.
- [`LootTableEvents.REPLACE`](#replacing-loot-tables): Verwerfe die ursprüngliche Tabelle und erstelle eine neue.
- [`LootTableEvents.MODIFY_DROPS`](#modifying-loot-table-drops): Ändere die finale Liste der `ItemStack`-Drops, nachdem die Beute generiert wurde.
- `LootTableEvents.ALL_LOADED`](#loot-table-post-processing): Inspiziere oder validiere alle Tabellen, nachdem der Ladevorgang abgeschlossen ist.

Diese Events treten während des Ladevorgangs der Beutetabelle in einer bestimmten Reihenfolge auf:

1. `REPLACE`
2. `MODIFY`
3. `ALL_LOADED`
4. `MODIFY_DROPS`

`MODIFY_DROPS` ist das einzige Event, das zur Laufzeit auftritt, während die anderen beim Laden der Welt auftreten.

Bei der Verwendung dieser Events ist es wichtig, diese Reihenfolge zu beachten, da sie Einfluss darauf hat, wie sich deine Änderungen auf andere Events und die ursprünglichen Beutetabellen auswirken.

## Beutetabellen bearbeiten {#modifying-loot-tables}

Verwende `LootTableEvents.MODIFY`, wenn du eine bestehende Beutetabelle ändern möchtest, während deren ursprünglicher Inhalt erhalten bleiben soll. Der Callback liefert dir einen `LootTable.Builder`, sodass du neue Pools hinzufügen oder Einträge zu bestehenden Pools hinzufügen kannst, ohne die gesamte Tabelle neu erstellen zu müssen.

Dies ist üblicherweise die beste Wahl, um Items zu Vanilla- oder Datenpaket-Tabellen hinzuzufügen, beispielsweise um ein benutzerdefiniertes Item zu den vorhandenen Beutedrops hinzuzufügen. Du kannst den Parameter `source` inspizieren, um festzustellen, ob die Tabelle aus eingebauten Ressourcen, einem Datenpaket oder einem anderen Ersetzen-Event stammt.

Verwende `MODIFY`, wenn die ursprüngliche Beutetabelle weitgehend unverändert bleiben soll.

Lasst uns zum Beispiel das Event `MODIFY` verwenden, um mithilfe des [Prädikats](#predicates) `LootItemEntityPropertyCondition` zu erreichen, dass beim Töten eines weißen Schafs mit einem Diamantschwert ein Diamant fallen gelassen wird.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_event

Effekte des obigen Beispiels:

<VideoPlayer src="/assets/develop/events/modify_event_example.webm">Beispiel des Modify-Event</VideoPlayer>

## Beutetabellen ersetzen {#replacing-loot-tables}

Verwende `LootTableEvents.REPLACE`, wenn du eine vorhandene Beutetabelle verwerfen und eine neue bereitstellen möchtest.

Der Callback erhält die originale `LootTable`. Gib einen neuen `LootTable` zurück, um sie zu ersetzen, oder gib `null` zurück, um sie unverändert zu lassen. Sobald ein Listener eine Tabelle ersetzt hat, werden keine weiteren Ersetzen-Listener für diese Tabelle aufgerufen.

Dieses Event ist nützlich, wenn die ursprüngliche Tabelle nicht mit dem Verhalten deines Mods kompatibel ist und das Anpassen einzelner Beute-Pools komplizierter wäre als das Erstellen einer neuen Tabelle.

Lasst uns zum Beispiel das Event `REPLACE` verwenden, um die Beutetabelle für ein braunes Schaf durch eine neue Tabelle zu ersetzen, bei der ein Goldbarren fallen gelassen wird, wenn das Schaf unter Verwendung des [Prädikats](#predicates) `LootItemEntityPropertyCondition` mit einem goldenen Schwert getötet wird.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_replace_event

::: warning

Gib immer `null` zurück, wenn du keine Beutetabelle ersetzt. Durch das Zurückgeben der ursprünglichen Tabelle wird die Beutetabelle weiterhin als ersetzt markiert, was verhindert, dass spätere Ersetzungs-Listener ausgeführt werden, und führt dazu, dass die Prüfung `isBuiltIn()` fehlschlägt.

:::

Effekte des obigen Beispiels:

<VideoPlayer src="/assets/develop/events/replace_event_example.webm">Beispiel eines Replace-Event</VideoPlayer>

## Drops von Beutetabellen bearbeiten {#modifying-loot-table-drops}

`LootTableEvents.MODIFY_DROPS` wird erst ausgeführt, wenn die Beute zur Laufzeit generiert wurde. Dieses Event ist nützlich, wenn:

- Die Anzahl der Beutetabellen unbekannt oder sehr hoch ist.
- Dieselben Regeln für viele Beutetabellen gelten sollen.
- Du den `LootContext`, wie beispielsweise die Entität, das Werkzeug oder die Schadensquelle, inspizieren möchtest.
- Es umständlich wäre, jeder Tabelle eine benutzerdefinierte Beutefunktion hinzuzufügen.

Die Drop-Liste kann direkt bearbeitet werden, indem man Item-Stacks hinzufügt, entfernt oder ändert. Beachte, dass dieses Event, da es nach der Beuteerzeugung läuft, weder die Pools, Einträge noch Bedingungen der Beutetabelle ändern kann.

::: info

Die Drops sind möglicherweise bereits in Stacks unterteilt, wenn in der Beutetabelle eine bestimmte Stackgröße festgelegt wurde.

:::

Zum Beispiel lasst uns das Event `MODIFY_DROPS` verwenden, um beim Zerstören eines Steinblocks zwei Steinblöcke statt Bruchstein fallen zu lassen.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_drops_event

Effekte des obigen Beispiels:

<VideoPlayer src="/assets/develop/events/modify_drops_event_example.webm">Beispiel des Modify Drops-Event</VideoPlayer>

## Nachbearbeitung der Beutetabelle {#loot-table-post-processing}

Verwende `LootTableEvents.ALL_LOADED` für Tätigkeiten, die ausgeführt werden sollen, nachdem alle Beutetabellen geladen wurden und nachdem die Events `REPLACE` und `MODIFY` ausgelöst wurden.

Das Event stellt den `ResourceManager` des Servers und die vollständige Beutetabellen-Registrierung bereit. Dies macht es geeignet, um geladene Tabellen zu inspizieren, sie zu validieren, Informationen zu sammeln oder um zusätzliche Einrichtungsschritte, die davon abhängen, dass alle Tabellen verfügbar sind, durchzuführen.

::: info

Dieses Event wird bei der Beuteerzeugung nicht zum Hinzufügen von Drops verwendet. Um eine Tabelle zu ändern, verwende [`MODIFY`](#modifying-loot-tables) oder [`REPLACE`](#replacing-loot-tables). Um generierte Item-Stacks zu ändern, verwende [`MODIFY_DROPS`](#modifying-loot-table-drops).

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_all_loaded_event

## Prädikate {#predicates}

Beute-Bedingungen, intern als Prädikate bezeichnet, legen fest, ob ein Beute-Pool, ein Eintrag oder eine Funktion verwendet werden kann. Sie sind besonders nützlich bei `MODIFY` und `REPLACE`, wo sie es ermöglichen, das Hinzufügen oder Ersetzen von Drops an Bedingungen zu knüpfen, ohne jeden Einzelfall im Java-Code behandeln zu müssen. Siehe die Aufrufe von `.when(...)` in den obigen Beispielen zu `MODIFY` und `REPLACE`. Die gleichen Bedingungen können bei der Erstellung von Ersatztabellen helfen, während bei `MODIFY_DROPS` entsprechende Prüfungen im Event-Callback durchgeführt werden müssen.

Unterhalb sind Beispiele für häufig verwendete Prädikate, gruppiert nach deren Zweck. Die vollständige Liste findest du im Paket `net.minecraft.world.level.storage.loot.predicates` und in der [Prädikatsliste des Minecraft-Wikis](https://minecraft.wiki/w/Predicate):

### Logik-Prädikate {#logic-predicates}

Diese kombinieren oder kehren andere Bedingungen um.

- `AllOfCondition`: Jede Bedingung muss erfüllt sein. Verwende dies, wenn du eine **UND**-Prüfung willst.
- `AnyOfCondition`: Mindestens eine Bedingung muss erfüllt sein. Verwende dies, wenn du eine **ODER**-Prüfung willst.
- `InvertedLootItemCondition`: Kehrt eine andere Bedingung um, sodass sie nur dann erfüllt ist, wenn die ursprüngliche Bedingung nicht erfüllt ist. Verwende dies, wenn du eine **NICHT**-Bedingung benötigst.

::: tip

Diese logischen Prädikate können verschachtelt werden, um komplexe Bedingungen zu erstellen. Zum Beispiel kannst du `AllOfCondition` und `AnyOfCondition` kombinieren, um eine Bedingung zu erstellen, bei der mehrere Prüfungen erfolgreich sein müssen, die jedoch eine gewisse Flexibilität bei den Anforderungen zulässt.

:::

### Weltzustandsprädikate {#world-state-predicates}

Diese prüfen bestimmte Dinge der Spielwelt oder die Position, an der Beute generiert wird.

- `WeatherCheck`: Prüft, ob es regnet oder donnert.
- `TimeCheck`: Prüft die Tageszeit oder einen Zeitraum.
- `LocationCheck`: Prüft, wo der Drop aufgetreten ist, beispielsweise die Y-Höhe oder andere Positionsdaten.
- `EnvironmentAttributeCheck`: Prüft weltspezifische Umgebungsregeln oder Attribute.

### Block-, Werkzeug- und Entitätsprädikate {#block-tool-and-entity-predicates}

Diese beziehen sich auf den zerstörten Block, das verwendete Werkzeug oder die Entität, welche die Beute verursacht hat.

- `ExplosionCondition`: Sorgt dafür, dass ein Beute-Pool oder ein Eintrag nur dann zur Anwendung kommt, wenn der Drop eine Explosion übersteht.
- `MatchTool`: Prüft, ob das Werkzeug, mit dem ein Block zerstört wurde, mit einem bestimmten Item oder einem Item-Prädikat übereinstimmt.
- `LootItemBlockStatePropertyCondition`: Prüft den Blockzustand vor dem Zerstören, was bei Feldfrüchten und anderen zustandsabhängigen Blöcken nützlich ist.
- `LootItemKilledByPlayerCondition`: Erfordert, dass die Entität von einem Spieler getötet wurde.
- `DamageSourceCondition`: Prüft Details zur Schadensquelle, wie beispielsweise, ob es sich um einen direkten oder indirekten Treffer handelte.

### Wahrscheinlichkeitsbasierte Prädikate {#chance-based-predicates}

Diese legen die Beute nach Wahrscheinlichkeit oder nach Verzauberungsstufe fest.

- `LootItemRandomChanceCondition`: Gibt eine feste, zufällige Wahrscheinlichkeit auf einen Drop.
- `LootItemRandomChanceWithEnchantedBonusCondition`: Passt die Wahrscheinlichkeit entsprechend der Verzauberungsstufe an.
- `BonusLevelTableCondition`: Ein Hilfsmittel für durch Verzauberungen skalierte Beutewahrscheinlichkeiten, wie beispielsweise Glück.

::: warning

`LootItemRandomChanceWithEnchantedBonusCondition` und `LootItemRandomChanceCondition` sollten nicht gemeinsam in demselben Pool verwendet werden, da beide die Grundwahrscheinlichkeit festlegen und dies zu unbeabsichtigtem Verhalten führen kann.

:::

### Geteilte Prädikatreferenzen {#shared-predicate-references}

- `ConditionReference`: Verweist auf eine datengetriebene Beute-Bedingung, die an anderer Stelle definiert und in mehreren Tabellen wiederverwendet wird.
