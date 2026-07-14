---
title: Mixin-Accessors
description: Lerne, wie du mit Mixins Accessors und Invokers auf Methoden und Felder zugreifst.
authors:
  - Bawnorton
  - bluebear94
  - cassiancc
  - MildestToucan
---

Mixins werden typischerweise verwendet, um bestehenden Code zu verändern und dessen Verhalten anzupassen oder zu erweitern. Mixin bietet jedoch auch Werkzeuge in Form von Accessor-Mixins, mit denen auf unzugängliche Felder und Methoden zugegriffen werden kann.

[Klassenoptimierer](../class-tweakers) bieten mit [Zugriffserweiterern](../class-tweakers/access-widening) ein ähnliches Werkzeug, wobei
die Accessors von Mixin kein Neuladen von Gradle erfordern und auch auf Ziele außerhalb von Minecraft angewendet werden können.

Eine Zugriffserweiterung ist weiterhin erforderlich, um finale Methoden zu überschreiben, finale Klassen zu Unterklassen zu machen oder auf private Klassen zu verweisen, da Accessors nur Felder und Methoden als Ziel haben können.

## Das Accessor-Interface erstellen {#creating-the-accessor-interface}

Accessor-Mixins müssen immer ein Interface sein und dürfen nur Methoden enthalten, die mit `@Accessor` oder `@Invoker` annotiert sind. Das Interface muss ähnlich wie andere Mixin-Klassen mit `@Mixin` annotiert werden.

Accessor-Interfaces werden üblicherweise nach deren Zielklasse mit dem Suffix `Accessor` benannt und in einem Unterpaket `accessor` innerhalb deines Mixin-Pakets abgelegt.
Zum Beispiel: `your.package.mixin.accessor`

## Feld-Accessors {#field-accessors}

Auf Felder kann über mit `@Accessor` annotierte Getter- und/oder Setter-Methoden zugegriffen werden:

::: tabs

== Instanzfeld

Getter-/Setter-Syntax:

Accessor-Methoden für Instanzfelder sollten mit deiner Mod-ID und einem Trennzeichen (üblicherweise `$` oder `_`) beginnen, um sicherzustellen, dass sie nicht mit irgendeiner anderen Methode kollidieren.

```java
@Accessor("<field name>")
FieldType example_mod$getFieldName();

@Accessor("<field name>")
void example_mod$setFieldName(FieldType value);
```

Beispiel:

<<< @/reference/latest/src/client/java/com/example/docs/mixin/client/accessor/GuiAccessor.java#mixin_accessors_instance_field_accessor_example

Verwendung:

<<< @/reference/latest/src/client/java/com/example/docs/accessor_usage/client/ExampleModAccessorUsageClient.java#mixin_accessors_instance_field_accessor_example_usage

== Statisches Feld

Getter-/Setter-Syntax:

Bei statischen Feldern ist der Präfix mit der Mod-ID nicht erforderlich, da statische Methoden nicht miteinander kollidieren können.

Damit der Java-Compiler zufrieden ist, lassen wir den Methodenkörper statischer Accessor-Methoden immer eine Ausnahme auslösen. Während der Anwendung werden diese Methoden jedoch immer von Mixin implementiert.

```java
@Accessor("<field name>")
static FieldType getFieldName() {
    throw new AssertionError("Untransformed @Accessor");
}

@Accessor("<field name>")
static void setFieldName(FieldType value) {
}
```

Beispiel:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/ClientboundCustomPayloadPacketAccessor.java#mixin_accessors_static_field_accessor_example

Verwendung:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_static_field_accessor_example_usage

:::

### Finale Felder setzen {#setting-final-fields}

Ist das Zielfeld final, annotiere die Setter-Methode zusätzlich mit `@Mutable`, um das final-Flag während der Anwendung zu entfernen:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/VillagerAccessor.java#mixin_accessors_mutable_setter_example

## Methoden-Invoker {#method-invokers}

Um auf unzugängliche Methoden oder Konstruktoren zuzugreifen, erstelle eine Methode mit derselben Signatur und annotiere sie mit `@Invoker`:

::: tabs

== Instanzmethode

Syntax:

Invoker-Methoden für Instanzmethoden sollten mit deiner Mod-ID und einem Trennzeichen (üblicherweise `$` oder `_`) beginnen, damit sie nicht mit anderen Methoden kollidieren.

Invoker-Methoden werden üblicherweise entweder direkt nach der Zielmethode benannt oder beginnen mit `invoke` oder `call`, gefolgt vom Methodennamen.

```java
@Invoker("<method name>")
MethodReturnType example_mod$methodName(/* matching parameters */)
```

Beispiel:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/InventoryAccessor.java#mixin_accessors_instance_invoker_example

Verwendung:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_instance_invoker_example_usage

== Statische Methode

Syntax:

Bei statischen Methoden ist der Präfix mit der Mod-ID nicht erforderlich, da statische Methoden nicht miteinander kollidieren können.

Damit der Java-Compiler zufrieden ist, lassen wir den Methodenkörper statischer Invoker-Methoden immer eine Ausnahme auslösen. Während der Anwendung werden diese Methoden jedoch immer von Mixin implementiert.

```java
@Invoker("<method name>")
static MethodReturnType invokeMethodName(/* matching parameters */) {
    throw new AssertionError("Untransformed @Accessor");
}
```

Beispiel:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/ShulkerBoxBlockAccessor.java#mixin_accessors_static_invoker_example

Verwendung:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_static_invoker_example_usage

== Konstruktor

Syntax:

Invoker-Methoden für Konstruktoren heißen üblicherweise `createTargetClass` oder `newTargetClass`, sind immer `static` und verwenden immer `<init>` als Wert der `@Invoker`-Annotation.

```java
@Invoker("<init>")
static TargetClass newTargetClass(/* matching parameters */)
```

Beispiel:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/IdentifierAccessor.java#mixin_accessors_constructor_invoker_example

Verwendung:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_constructor_invoker_example_usage

:::

## Accessors für final-Klassen verwenden {#accessors-for-final-classes}

Da der Java-Compiler bei final-Klassen strenger ist, erlaubt er uns nicht direkt die Instanzen in ein Accessor-Interface umwandeln.

Um dies zu umgehen, müssen wir zunächst nur in `Object` und anschließend in das Accessor-Interface umwandeln:

```java
((TargetClassAccessor) (Object) targetClassInstance).example_mod$accessorMethod(/* ... */)
```
