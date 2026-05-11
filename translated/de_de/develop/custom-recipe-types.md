---
title: Benutzerdefinierte Rezepttypen
description: Lerne, wie du einen benutzerdefinierten Rezepttyp erstellst.
authors:
  - cassiancc
  - skippyall
---

Benutzerdefinierte Rezepttypen bieten die Möglichkeit, datengesteuerte Rezepte für die benutzerdefinierten Herstellungsmechaniken deines Mods zu erstellen. Als Beispiel erstellen wir einen Rezepttyp für einen Aufwertungsblock, ähnlich wie bei einem Schmiedetisch.

## Erstellen einer Eingabeklasse für das Rezept {#creating-your-recipe-input-class}

Bevor wir mit der Erstellung unseres Rezepts beginnen können, benötigen wir eine Implementierung von `RecipeInput`, die die Eingabeitems im Inventar unseres Blocks aufnehmen kann. Wir möchten, dass ein Aufwertungsrezept zwei Eingabeitems enthält: Ein Basisitem, das aufgewertet werden soll, und die Aufwertung selbst.

@[code transcludeWith=:::recipeInput](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeInput.java)

## Die Rezeptklasse erstellen {#creating-the-recipe-class}

Da wir nun eine Möglichkeit haben, die Eingabeitems zu speichern, können wir unsere `Recipe`-Implementierung erstellen. Implementierungen dieser Klasse stellen ein einzelnes Rezept dar, das in einem Datenpaket definiert ist. Sie sind dafür verantwortlich, die Zutaten und Vorgaben des Rezepts zu überprüfen und das in das Ergebnis zusammenzubauen.

Beginnen wir damit, das Ergebnis und die Zutaten des Rezepts zu definieren.

@[code transcludeWith=:::baseClass](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Beachte, dass wir für unsere Eingaben `Ingredient`-Objekte verwenden. Dadurch kann unser Rezept mehrere Items beliebig austauschbar verarbeiten.

## Die Methoden implementieren {#implementing-the-methods}

Als Nächstes implementieren wir die Methoden aus dem Rezept-Interface. Die Interessanten sind `matches` und `assemble`. Die Methode `matches` prüft, ob die Eingabeitems aus unserer `RecipeInput`-Implementierung mit unseren Zutaten übereinstimmen. Die Methode `assemble` erstellt dann den resultierenden `ItemStack`.

Um zu prüfen, ob die Zutaten übereinstimmen, können wir die Methode `test` unserer Zutaten verwenden.

@[code transcludeWith=:::implementing](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Erstellen eines Rezept-Serialisierers {#creating-a-recipe-serializer}

Der Rezept-Serializer verwendet einen [`MapCodec`](./codecs/#mapcodec), um das Rezept aus JSON zu lesen, und einen `StreamCodec`, um es über das Netzwerk zu senden.

Wir werden `RecordCodecBuilder#mapCodec` verwenden, um einen Map-Codec für unser Rezept zu erstellen. Dadurch können wir die vorhandenen Codecs von Minecraft in unsere eigenen integrieren:

@[code transcludeWith=:::mapCodec](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Der Stream-Codec kann auf ähnliche Weise mit `StreamCodec#composite` erstellt werden:

@[code transcludeWith=:::streamCodec](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Nun registrieren wir den Rezept-Serializer sowie einen Rezepttyp. Das kannst du im Initialisierer deines Mods tun oder in einer separaten Klasse, deren Methode vom Initialisierer deines Mods aufgerufen wird:

@[code transcludeWith=:::registration](@/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java)

Zurück zu unserer Rezeptklasse: Wir können nun die Methoden hinzufügen, die die soeben registrierten Objekte zurückgeben:

@[code transcludeWith=:::implementRegistryObjects](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Um unseren benutzerdefinierten Rezepttyp zu vervollständigen, müssen wir nur noch die verbleibenden Methoden `placementInfo`, `showNotification`, `group` und `recipeBookCategory` implementieren, die vom Rezeptbuch verwendet werden, um unser Rezept auf einem Bildschirm zu platzieren. Vorerst geben wir einfach `PlacementInfo.NOT_PLACEABLE` und `null` zurück, da das Rezeptbuch nicht ohne Weiteres auf modifizierte Arbeitsplätze ausgeweitet werden kann. Außerdem werden wir die Methode `isSpecial` überschreiben, sodass sie `true` zurückgibt, um zu verhindern, dass bestimmte andere, mit dem Rezeptbuch zusammenhängende Logik ausgeführt wird und Fehler protokolliert.

@[code transcludeWith=:::recipeBook](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Ein Rezept erstellen {#creating-a-recipe}

Unser Rezepttyp funktioniert jetzt, aber es fehlen noch zwei wichtige Dinge: Ein Rezept für unseren Rezepttyp und eine Möglichkeit, es herzustellen.

Lasst uns zuerst ein Rezept erstellen. Erstelle in deinem Ordner `resources` im Verzeichnis `data/example-mod/recipe` eine Datei mit der Dateiendung `.json`. Jede JSON-Datei für ein Rezept enthält einen Schlüssel `"type"`, der auf den Rezept-Serialisierer des jeweiligen Rezepts verweist. Die übrigen Schlüssel werden durch den Codec des jeweiligen Rezept-Serialisierers definiert.

In unserem Fall sieht eine gültige Rezeptdatei wie folgt aus:

@[code](@/reference/latest/src/main/resources/data/example-mod/recipe/upgrading/diamond_pickaxe.json)

## Ein Menü erstellen {#creating-a-menu}

::: info

Weitere Informationen zum Erstellen von Menüs findest du unter unter [Container-Menüs](blocks/container-menus).

:::

Damit wir unser Rezept in dem GUI erstellen können, erstellen wir einen Block mit einem [Menü](./blocks/container-menus):

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java)

Da gibt es eine Menge zu besprechen! Dieses Menü verfügt über zwei Eingabefelder und ein Ausgabefeld.

Der Eingabecontainer ist eine anonyme Unterklasse von `SimpleContainer`, die bei einer Änderung ihrer Items die Methode `slotsChanged` des Menüs aufruft. In `slotsChanged` erstellen wir dann eine Instanz unserer Rezept-Eingabeklasse und füllen sie mit den beiden Eingabefeldern.

Um zu prüfen, ob es mit einem Rezept übereinstimmt, stellen wir zunächst sicher, dass wir uns auf der Serverebene befinden, da Clients nicht wissen, welche Rezepte vorhanden sind. Anschließend rufen wir den `RecipeManager` über `serverLevel.recipeAccess()` ab.

Wir rufen `serverLevel.recipeAccess().getRecipeFor` mit unseren Rezept-Eingaben auf, um ein Rezept zu erhalten, das den Eingaben entspricht. Wenn ein Rezept gefunden wurde, können wir das Ergebnis zum Ergebniscontainer hinzufügen oder daraus entfernen.

Um zu erkennen, wann der Benutzer das Ergebnis entnimmt, erstellen wir eine anonyme Unterklasse von `Slot`. Die Methode `onTake` unseres Menüs entfernt dann die Eingabeitems.

Um zu verhindern, dass Items gelöscht werden, ist es wichtig, die Eingaben beim Schließen des Bildschirms wieder zurückzusetzen, wie in der Methode `removed` gezeigt.

## Rezeptsynchronisierung {#recipe-synchronization}

::: info

Dieser Abschnitt ist optional und nur erforderlich, wenn du möchtest, dass die Clients über Rezepte informiert werden.

:::

Wie bereits erwähnt, werden Rezepte vollständig auf dem logischen Server verarbeitet. In manchen Fällen muss ein Client jedoch wissen, welche Rezepte es gibt - ein Beispiel aus Vanilla sind Steinsägen, das die verfügbaren Rezeptoptionen für eine bestimmte Zutat anzeigen muss. Außerdem laufen die Plugins bestimmter Rezeptbetrachter, darunter [JEI](https://modrinth.com/mod/jei), auf dem logischen Client, sodass du die Rezept-Synchronisations-API von Fabric verwenden musst.

Um deine Rezepte zu synchronisieren, rufe einfach `RecipeSynchronization.synchronizeRecipeSerializer` in deinem Mod-Initialisierer auf und gib den Rezept-Serialisierer deines Mods an:

@[code transcludeWith=:::recipeSync](@/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java)

Nach der Synchronisierung können die Rezepte jederzeit über den Rezeptmanager auf Client-Ebene abgerufen werden:

@[code transcludeWith=:::recipeSyncClient](@/reference/latest/src/client/java/com/example/docs/ExampleModRecipesClient.java)
