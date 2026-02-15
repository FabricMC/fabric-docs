---
title: Benutzerdefinierte Datenkomponenten
description: Lerne, wie du benutzerdefinierte Datenkomponenten zu deinen Items mit Hilfe des neuen 1.20.5 Komponenten-System hinzufügst.
authors:
  - Romejanic
  - ekulxam
---

Je komplexer deine Items werden, desto mehr benutzerdefinierte Daten musst du vielleicht für jedes Item speichern. Das Spiel erlaubt es, persistente Daten in einem `ItemStack` zu speichern, und seit der Version 1.20.5 tun wir das mit Hilfe von **Datenkomponenten**.

Datenkomponenten ersetzen NBT-Daten aus früheren Versionen durch strukturierte Datentypen, die auf einen `ItemStack` angewendet werden können, um dauerhafte Daten über diesen Stack zu speichern. Datenkomponenten sind namensgebunden, was bedeutet, dass wir unsere eigenen Datenkomponenten implementieren können, um benutzerdefinierte Daten über einen `ItemStack` zu speichern und später darauf zuzugreifen. Eine vollständige Liste der Vanilla-Datenkomponenten kann auf dieser [Minecraft-Wiki-Seite](https://minecraft.wiki/w/Data_component_format#List_of_components) gefunden werden.

Neben der Registrierung von benutzerdefinierten Komponenten wird auf dieser Seite auch die allgemeine Verwendung der Komponenten-API behandelt, die auch für Vanilla-Komponenten gilt. Du kannst die Definitionen aller Vanilla-Komponenten in der Klasse `DataComponents` sehen und darauf zugreifen.

## Eine Komponente registrieren {#registering-a-component}

Wie bei allem anderen in deinem Mod musst du deine benutzerdefinierte Komponente mit einem `DataComponentType` registrieren. Dieser Komponententyp nimmt ein generisches Argument entgegen, das den Typ des Wertes deiner Komponente enthält. Darauf werden wir weiter unten bei der Behandlung von [einfachen](#basic-data-components) und [fortgeschrittenen](#advanced-data-components) Komponenten näher eingehen.

Wähle eine sinnvolle Klasse, in der du dies unterbringen kannst. Für dieses Beispiel werden wir ein neues Paket namens `component` und eine Klasse erstellen, die alle unsere Komponententypen enthält und `ModComponents` heißt. Stelle sicher, dass du `ModComponents.initialize()` in deinem [Mod-Initialisierer](../getting-started/project-structure#entrypoints) aufrufst.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Dies ist die grundlegende Vorlage für die Registrierung eines Component Typs:

```java
public static final DataComponentType<?> MY_COMPONENT_TYPE = Registry.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "my_component"),
    DataComponentType.<?>builder().persistent(null).build()
);
```

Hier gibt es einige Dinge zu beachten. In der ersten und vierten Zeile ist ein `?` zu sehen. Dieser wird durch den Typ des Wertes deiner Komponente ersetzt. Wir werden das bald befüllen.

Zweitens musst du einen `Identifier` angeben, der die beabsichtigte ID deiner Komponente enthält. Diese ist mit der Mod-ID deines Mods verknüpft.

Schließlich haben wir einen `DataComponentType.Builder`, der die eigentliche Instanz von `DataComponentType` erstellt, die registriert wird. Dies enthält ein weiteres wichtiges Detail, das wir besprechen müssen: den `Codec`. deiner Komponente. Dies ist derzeit `null`, aber wir werden es auch bald befüllen.

## Einfache Datenkomponenten {#basic-data-components}

Einfache Datenkomponenten (wie `minecraft:damage`) bestehen aus einzelnen Datenwerten, wie einem `int`, `float`, `boolean` oder `String`.

Als Beispiel wollen wir einen `Integer`-Wert erstellen, der festhält, wie oft der Spieler mit der rechten Maustaste geklickt hat, während er unseren Gegenstand hielt. Aktualisieren wir unsere Komponentenregistrierung wie folgt:

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Du kannst sehen, dass wir jetzt `<Integer>` als unseren generischen Typ übergeben, was anzeigt, dass diese Komponente als ein einzelner `int` Wert gespeichert wird. Für unseren Codec verwenden wir den mitgelieferten `DataComponents.POSITIVE_INT` Codec. Für einfache Komponenten wie diese können wir mit einfachen Codecs auskommen, aber komplexere Szenarien erfordern möglicherweise einen benutzerdefinierten Codec (dies wird später kurz behandelt).

Wenn du das Spiel startest, solltest du einen Befehl wie diesen eingeben können:

![/give Befehl, welcher due benutzerdefinierte Komponente zeigt](/assets/develop/items/custom_component_0.png)

Wenn du den Befehl ausführst, solltest du das Element erhalten, das die Komponente enthält. Allerdings verwenden wir unsere Komponente derzeit nicht für irgendetwas Nützliches. Beginnen wir damit, den Wert der Komponente so zu lesen, dass wir ihn sehen können.

## Den Komponentenwert lesen {#reading-component-value}

Fügen wir ein neues Item hinzu, das den Zähler jedes Mal erhöht, wenn es mit der rechten Maustaste angeklickt wird. Du solltest die Seite [Benutzerdefinierte Iteminteraktionen](./custom-item-interactions) lesen, die die Techniken behandelt, die wir in diesem Leitfaden verwenden werden.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Denke wie üblich daran, das Item in deiner Klasse `ModItems` zu registrieren.

```java
public static final Item COUNTER = register("counter", CounterItem::new, new Item.Properties());
```

Wir werden einen Tooltip-Code hinzufügen, um den aktuellen Wert der Klickzahl anzuzeigen, wenn wir mit dem Mauszeiger über unser Item im Inventar fahren. Wir können die Methode `get()` auf unserem `ItemStack` verwenden, um den Wert unserer Komponente wie folgt zu erhalten:

```java
int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
```

Dadurch wird der aktuelle Wert der Komponente als der Typ zurückgegeben, den wir bei der Registrierung unserer Komponente definiert haben. Diesen Wert können wir dann verwenden, um einen Tooltip-Eintrag hinzuzufügen. Füge diese Zeile der Methode `appendHoverText` in der Klasse `CounterItem` hinzu:

```java
public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
  int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
  textConsumer.accept(Component.translatable("item.example-mod.counter.info", count).withStyle(ChatFormatting.GOLD));
}
```

::: warning

Ab Version 1.21.5 ist `appendHoverText` veraltet. Es wird nun empfohlen, `TooltipProvider` wie folgt zu implementieren. Dies erfordert die [Erstellung einer benutzerdefinierten Komponentenklasse](#advanced-data-components).

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ComponentWithTooltip.java)

Dann kannst du den `TooltipProvider` über `ComponentTooltipAppenderRegistry` registrieren. Dies wird in `onInitialize` im `ModInitializer` aufgerufen.

@[code lang=java transcludeWith=#tooltip_provider](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

Alternativ kannst du `ItemTooltipCallback` verwenden, um `appendHoverText` zu ersetzen. Dies wird in `onInitializeClient` im `ClientModInitializer` aufgerufen.

@[code lang=java transcludeWith=#tooltip_provider_client](@/reference/latest/src/client/java/com/example/docs/ExampleModClient.java)

:::

Vergiss nicht, deine Sprachdatei (`/assets/xample-mod/lang/en_us.json`) zu aktualisieren und diese zwei Zeilen hinzuzufügen:

```json
{
  "item.example-mod.counter": "Counter",
  "item.example-mod.counter.info": "Used %1$s times"
}
```

Starte das Spiel und führe diesen Befehl aus, um dir ein neues Zähler Item mit einer Anzahl von 5 zu geben.

```mcfunction
/give @p example-mod:counter[example-mod:click_count=5]
```

Wenn du den Mauszeiger über dieses Item in deinem Inventar bewegst, solltest du die Anzahl im Tooltip sehen!

![Tooltip zeigt "Used 5 times"](/assets/develop/items/custom_component_1.png)

Wenn du dir jedoch ein neues Counter Item _ohne_ die benutzerdefinierte Komponente gibst, stürzt das Spiel ab, wenn du den Mauszeiger über den Gegenstand in deinem Inventar bewegst. Im Absturzbericht sollte ein Fehler wie dieser angezeigt werden:

```log
java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "net.minecraft.world.item.ItemStack.get(net.minecraft.core.component.DataComponentType)" is null
        at com.example.docs.item.custom.CounterItem.appendHoverText(LightningStick.java:45)
        at net.minecraft.world.item.ItemStack.getTooltipLines(ItemStack.java:767)
```

Da der `ItemStack` derzeit keine Instanz unserer benutzerdefinierten Komponente enthält, wird der Aufruf von `stack.get()` mit unserem Komponententyp erwartungsgemäß `null` zurückgeben.

Es gibt drei Lösungen, mit denen wir dieses Problem angehen können.

### Ein Standard Wert für die Komponente setzen {#setting-default-value}

Wenn du deinen Artikel registrierst und ein `Item.Properties`-Objekt an deinen Item Konstruktor übergibst, kannst du auch eine Liste von Standardkomponenten angeben, die auf alle neuen Items angewendet werden. Wenn wir zu unserer Klasse `ModItems` zurückkehren, wo wir das `CounterItem`, registrieren, können wir einen Standardwert für unsere benutzerdefinierte Komponente hinzufügen. Füge dies hinzu, damit bei neuen Einträgen die Anzahl `0` angezeigt wird.

@[code transcludeWith=::\_13](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Wenn ein neues Item erstellt wird, wird automatisch unsere benutzerdefinierte Komponente mit dem angegebenen Wert angewendet.

::: warning

Mit Hilfe von Befehlen ist es möglich, eine Standardkomponente aus einem `ItemStack` zu entfernen. In den nächsten beiden Abschnitten erfährst du, wie du vorgehen musst, wenn die Komponente in deinem Item nicht vorhanden ist.

:::

### Lesen mit einem Standardwert {#reading-default-value}

Außerdem können wir beim Lesen des Komponentenwerts die Methode `getOrDefault()` auf unserem Objekt `ItemStack` verwenden, um einen bestimmten Standardwert zurückzugeben, wenn die Komponente nicht auf dem Stack vorhanden ist. Dadurch werden Fehler vermieden, die durch eine fehlende Komponente entstehen. Wir können unseren Tooltip-Code wie folgt anpassen:

```java
int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
```

Wie du sehen kannst, benötigt diese Methode zwei Argumente: Unseren Komponententyp wie zuvor und einen Standardwert, der zurückgegeben wird, wenn die Komponente nicht vorhanden ist.

### Prüfen, ob eine Komponente existiert {#checking-if-component-exists}

Mit der Methode `has()` kann auch geprüft werden, ob eine bestimmte Komponente auf einem `ItemStack` vorhanden ist. Sie nimmt den Komponententyp als Argument und gibt `true` oder `false` zurück, je nachdem, ob der Stack diese Komponente enthält.

```java
boolean exists = stack.has(ModComponents.CLICK_COUNT_COMPONENT);
```

### Den Fehler beheben {#fixing-the-error}

Wir fahren mit der dritten Option fort. Wir fügen also nicht nur einen Standardkomponentenwert hinzu, sondern prüfen auch, ob die Komponente auf dem Stack vorhanden ist, und zeigen den Tooltip nur an, wenn dies der Fall ist.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Starte das Spiel erneut und fahre mit dem Mauszeiger über das Item ohne die Komponente. Du solltest sehen, dass er "Used 0 times" anzeigt und das Spiel nicht mehr abstürzt.

![Tooltip zeigt "Used 0 times"](/assets/develop/items/custom_component_2.png)

Versuche, dir selbst einen Counter zu geben, indem du unsere benutzerdefinierte Komponente entfernst. Du kannst diesen Befehl nutzen, um dies zu tun:

```mcfunction
/give @p example-mod:counter[!example-mod:click_count]
```

Wenn du den Mauszeiger über dieses Element bewegst, sollte der Tooltip fehlen.

![Counter Item ohne Tooltip](/assets/develop/items/custom_component_7.png)

## Einen Komponentenwert aktualisieren {#setting-component-value}

Lass uns nun versuchen, unseren Komponentenwert zu aktualisieren. Wir werden die Anzahl der Klicks jedes Mal erhöhen, wenn wir unser Counter Item verwenden. Um den Wert einer Komponente auf einem `ItemStack` zu ändern, verwenden wir die Methode `set()` wie folgt:

```java
stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Hier wird unser Komponententyp und der Wert, auf den wir ihn setzen wollen, verwendet. In diesem Fall ist das unsere neue Klickzahl. Diese Methode gibt auch den alten Wert der Komponente zurück (falls vorhanden), was in manchen Situationen nützlich sein kann. Zum Beispiel:

```java
int oldValue = stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

Richten wir eine neue Methode `use()` ein, um die alte Klickzahl zu lesen, sie um eins zu erhöhen und dann die aktualisierte Klickzahl zu setzen.

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

Versuche nun, das Spiel zu starten und klicke mit der rechten Maustaste auf das Counter-Item in deiner Hand. Wenn du dein Inventar öffnest und dir das Item noch einmal ansiehst, solltest du sehen, dass die Nutzungszahl um die Anzahl der Klicks gestiegen ist, die du darauf gemacht hast.

![Tooltip zeigt "Used 8 times"](/assets/develop/items/custom_component_3.png)

## Einen Komponentenwert entfernen {#removing-component-value}

Du kannst auch eine Komponente von deinem `ItemStack` entfernen, wenn sie nicht mehr benötigt wird. Dies geschieht mit der Methode `remove()`, die deinen Komponententyp entgegennimmt.

```java
stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

Diese Methode gibt auch den Wert der Komponente zurück, bevor sie entfernt wird, sodass du sie auch wie folgt verwenden kannst:

```java
int oldCount = stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

## Fortgeschrittene Datenkomponenten {#advanced-data-components}

Möglicherweise musst du mehrere Attribute in einer einzigen Komponente speichern. Als Vanilla-Beispiel speichert die Komponente `minecraft:food` mehrere Werte in Bezug auf Nahrung, wie `nutrition`, `saturation`, `eat_seconds` und mehr. In diesem Leitfaden werden sie als "zusammengesetzte" Komponenten bezeichnet.

Für zusammengesetzte Komponenten musst du eine `record`-Klasse erstellen, um die Daten zu speichern. Dies ist der Typ, den wir in unserem Komponententyp registrieren und den wir lesen und schreiben werden, wenn wir mit einem `ItemStack` interagieren. Beginne mit der Erstellung einer neuen Record-Klasse im Paket `component`, das wir zuvor erstellt haben.

```java
public record MyCustomComponent() {
}
```

Beachte, dass sich nach dem Klassennamen Klammern befinden. Hier definieren wir die Liste der Eigenschaften, die unsere Komponente haben soll. Fügen wir eine Fließkommazahl und einen booleschen Wert mit den Bezeichnungen `temperature` und `burnt` hinzu.

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Da wir eine benutzerdefinierte Datenstruktur definieren, gibt es für unseren Anwendungsfall keinen bereits vorhandenen `Codec` wie bei den [einfachen Komponenten](#basic-data-components). Das bedeutet, dass wir unseren eigenen Codec konstruieren müssen. Definieren wir einen in unsere Record-Klasse mit einem `RecordCodecBuilder`, auf den wir verweisen können, sobald wir die Komponente registrieren. Weitere Einzelheiten zur Verwendung eines `RecordCodecBuilder` findest du in [diesem Abschnitt der Codecs-Seite](../codecs#merging-codecs-for-record-like-classes).

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

Du kannst sehen, dass wir eine Liste von benutzerdefinierten Feldern auf der Grundlage der primitiven `Codec`-Typen definieren. Wir teilen dem Spiel jedoch auch mit, wie unsere Felder heißen, indem wir `fieldOf()` verwenden und dann `forGetter()` benutzen, um dem Spiel mitzuteilen, welches Attribut unseres Datensatzes es füllen soll.

Du kannst auch optionale Felder definieren, indem du `optionalFieldOf()` verwendest und einen Standardwert als zweites Argument übergibst. Alle Felder, die nicht als optional gekennzeichnet sind, werden benötigt, wenn die Komponente mit `/give` erstellt wird, also stelle sicher, dass du alle optionalen Argumente als solche kennzeichnest, wenn du deinen Codec erstellst.

Schließlich rufen wir `apply()` auf und übergeben den Konstruktor unseres Datensatzes. Weitere Einzelheiten über die Erstellung von Codecs und fortgeschrittene Anwendungsfälle findest du auf der Seite [Codecs](../codecs).

Die Registrierung einer zusammengesetzten Komponente ist ähnlich wie zuvor. Wir übergeben einfach unsere Record-Klasse als generischen Typ und unseren benutzerdefinierten `Codec` an die Methode `codec()`.

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

Starte jetzt das Spiel. Versuche, die Komponente mit dem Befehl `/give` anzuwenden. Zusammengesetzte Komponentenwerte werden als ein mit `{}` umschlossenes Objekt übergeben. Wenn du leere geschweifte Klammern einfügst, wird eine Fehlermeldung angezeigt, die besagt, dass der erforderliche Schlüssel `temperature` fehlt.

![Give-Befehl, der den fehlenden Schlüssel "temperature" zeigt](/assets/develop/items/custom_component_4.png)

Füge dem Objekt einen Temperaturwert mit dem Syntax `temperature:8.2` hinzu. Du kannst auch optional einen Wert für `burnt` mit dem gleichen Syntax übergeben, aber entweder `true` oder `false`. Du solltest nun sehen, dass der Befehl gültig ist und dir ein Element mit der Komponente ausgeben kann.

![Gültiger give-Befehl, der beide Eigenschaften zeigt](/assets/develop/items/custom_component_5.png)

### Abrufen, setzen und entfernen von fortgeschrittenen Komponenten {#getting-setting-removing-advanced-comps}

Die Verwendung der Komponente im Code ist die gleiche wie zuvor. Die Verwendung von `stack.get()` gibt eine Instanz der Klasse `record` zurück, die Sie dann zum Lesen der Werte verwenden können. Da Datensätze schreibgeschützt sind, musst du eine neue Instanz deines Datensatzes erstellen, um die Werte zu aktualisieren.

```java
// read values of component
MyCustomComponent comp = stack.get(ModComponents.MY_CUSTOM_COMPONENT);
float temp = comp.temperature();
boolean burnt = comp.burnt();

// set new component values
stack.set(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(8.4f, true));

// check for component
if (stack.contains(ModComponents.MY_CUSTOM_COMPONENT)) {
    // do something
}

// remove component
stack.remove(ModComponents.MY_CUSTOM_COMPONENT);
```

Du kannst auch einen Standardwert für eine zusammengesetzte Komponente festlegen, indem du ein Komponenten-Objekt an deine `Item.Properties` übergibst. Zum Beispiel:

```java
public static final Item COUNTER = register(
    "counter",
    CounterItem::new,
    new Item.Properties().component(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(0.0f, false))
);
```

Jetzt kannst du benutzerdefinierte Daten an einem `ItemStack` speichern. Nutze dies mit Bedacht!

![Item zeigt einen Tooltip für die Klickzahl, Temperatur und Brennzustand](/assets/develop/items/custom_component_6.png)
