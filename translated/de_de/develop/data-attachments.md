---
title: Datenanhänge
description: Ein Leitfaden, der die grundlegende Verwendung der neuen Data Attachment API von Fabric abbildet.
authors:
  - cassiancc
  - DennisOchulor
---

Die Data Attachment API ist eine neue und experimentelle Ergänzung der Fabric API. Sie ermöglicht Entwicklern, beliebige Daten einfach an Entitäten, Blockentitäten, Levels und Chunks anzuhängen. Die angehängten Daten können über [Codecs](./codecs) und Stream-Codecs gespeichert und synchronisiert werden. Du solltest sich daher vor der Verwendung mit diesen vertraut machen.

## Einen Datenanhang erstellen {#creating-attachments}

Du beginnst mit einem Aufruf von `AttachmentRegistry.create`. Das folgende Beispiel erstellt einen einfachen Datenanhang, der nicht über Neustarts hinweg synchronisiert und persistent bleibt.

@[code lang=java transcludeWith=:::string](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

`AttachmentRegistry` enthält einige Methoden zum Erstellen grundlegender Datenanhänge, darunter:

- `AttachmentRegistry.create()`: Erstellt einen Datenanhang. Durch einen Neustart des Spiels wird der Anhang gelöscht.
- `AttachmentRegistry.createPersistent()`: Erstellt einen Datenanhang, der zwischen Neustarts des Spiels persistent bleibt.
- `AttachmentRegistry.createDefaulted()`: Erstellt einen Datenanhang mit einem Standardwert, den du mit `getAttachedOrCreate` lesen kannst. Durch einen Neustart des Spiels wird der Anhang gelöscht.

Das Verhalten jeder Methode kann auch mit dem Parameter `builder` von `create` nachgebildet und weiter angepasst werden, indem das [Muster der Verkettung von Methoden](https://en.wikipedia.org/wiki/Method_chaining) angewendet wird.

### Einen Datenanhang synchronisieren {#syncing-attachments}

Wenn du möchtest, dass ein Datenanhang sowohl persistent ist als auch zwischen Server und Clients synchronisiert wird, kannst du dieses Verhalten mit der Methode `create` festlegen, die die Konfiguration über eine `builder`-Kette ermöglicht. Zum Beispiel:

@[code lang=java transcludeWith=:::pos](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

Das obige Beispiel synchronisiert mit jedem Spieler, aber das passt möglicherweise nicht zu deinem Anwendungsfall. Hier sind einige weitere Standardprädikate, aber du kannst auch eigene bauen, indem du auf die Klasse `AttachmentSyncPredicate` verweist.

- `AttachmentSyncPredicate.all()`: Synchronisiert die Anhänge mit allen Clients.
- `AttachmentSyncPredicate.targetOnly()`: Synchronisiert den Anhang nur mit dem Ziel, an das er angehängt ist. Beachte, dass die Synchronisierung nur erfolgen kann, wenn das Ziel ein Spieler ist.
- `AttachmentSyncPredicate.allButTarget()`: Synchronisiert den Anhang mit allen Clients außer mit dem Ziel, an das er angehängt ist. Beachte, dass die Ausnahme nur gelten kann, wenn das Ziel ein Spieler ist.

### Datenanhänge persistieren {#persisting-attachments}

Datenanhänge können auch so eingestellt werden, dass sie über Neustarts des Spiel hinweg bestehen bleiben, indem die Methode `persistent` in der Builder-Kette aufgerufen wird. Es nimmt einen `Codec` entgegen, damit das Spiel weiß, wie die Daten serialisiert werden müssen.

Mit der Methode `copyOnDeath` können sie so eingestellt werden, dass sie auch nach dem Tod oder der [Konvertierung](https://minecraft.wiki/w/Mob_conversion) des Ziels bestehen bleiben.

@[code lang=java transcludeWith=:::persistent](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

## Lesen von einem Datenanhang {#reading-attachments}

Methoden zum Lesen aus einem Datenanhang wurden in die Klassen `Entity`, `BlockEntity`, `ServerLevel` und `ChunkAccess` eingefügt. Die Verwendung ist so einfach wie der Aufruf einer der Methoden, die den Wert der angehängten Daten zurückgeben.

```java
// Checks if the given AttachmentType has attached data, returning a boolean.
entity.hasAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, or `null` if it doesn't exist.
entity.getAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, throwing a `NullPointerException` if it doesn't exist.
entity.getAttachedOrThrow(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, setting the value if it doesn't exist.
entity.getAttachedOrSet(EXAMPLE_STRING_ATTACHMENT, "basic");
entity.getAttachedOrSet(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0););

// Gets the data associated with the given AttachmentType, returning the provided value if it doesn't exist.
entity.getAttachedOrElse(EXAMPLE_STRING_ATTACHMENT, "basic");
entity.getAttachedOrElse(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0););
```

## Schreiben zu einem Datenanhang {#writing-attachments}

Methoden zum Schreiben in einen Datenanhang wurden in die Klassen `Entity`, `BlockEntity`, `ServerLevel` und `ChunkAccess` eingefügt. Durch den Aufruf einer der folgenden Methoden wird der Wert der angehängten Daten aktualisiert und der vorherige Wert zurückgegeben (oder `null`, wenn kein Wert vorhanden war).

```java
// Sets the data associated with the given AttachmentType, returning the previous value.
entity.setAttached(EXAMPLE_STRING_ATTACHMENT, "new value");

// Modifies the data associated with the given AttachmentType in place, returning the currently attached value. Note that currentValue is null if there is no previously attached data.
entity.modifyAttached(EXAMPLE_STRING_ATTACHMENT, currentValue -> "The length was " + (currentValue == null ? 0 : currentValue.length()));

// Removes the data associated with the given AttachmentType, returning the previous value.
entity.removeAttached(EXAMPLE_STRING_ATTACHMENT);
```

::: warning

Du solltest für Datenanhänge immer Werte mit unveränderlichen Typen verwenden und diese auch nur mit API-Methoden aktualisieren. Andernfalls kann es dazu kommen, dass die Datenanhänge nicht dauerhaft gespeichert oder nicht ordnungsgemäß synchronisiert werden.

:::

## Größere Anhänge {#larger-attachments}

Obwohl Datenanhänge jede Art von Daten speichern können, für die ein Codec geschrieben werden kann, glänzen sie besonders bei der Synchronisierung einzelner Werte. Der Grund dafür ist, dass ein Datenanhang unveränderlich ist: Die Änderung eines Teils seines Werts (z. B. eines einzelnen Feldes eines Objekts) bedeutet, dass er vollständig ersetzt werden muss, was eine vollständige Synchronisierung mit jedem Client auslöst, der ihn verfolgt.

Stattdessen kannst du komplexere Anhänge erstellen, indem du sie in mehrere Felder aufteilst und mit einer Hilfsklasse organisierst. Wenn du beispielsweise zwei Felder benötigst, die sich auf die Kondition eines Spielers beziehen, kannst du etwa Folgendes erstellen:

@[code lang=java transcludeWith=:::stamina](@/reference/latest/src/main/java/com/example/docs/attachment/Stamina.java)

Diese Hilfsklasse kann dann wie folgt verwendet werden:

```java
Player player = getPlayer();
Stamina.get(player).getCurrentStamina();
```
