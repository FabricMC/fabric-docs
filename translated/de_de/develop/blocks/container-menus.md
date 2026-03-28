---
title: Containermenüs
description: Ein Leitfaden, in der erklärt wird, wie man ein einfaches Menü für einen Containerblock erstellt.
authors:
  - CelDaemon
  - Tenneb22
resources:
  https://docs.neoforged.net/docs/inventories/menus: Menüs - NeoForge Docs
---

<!---->

:::info VORAUSSETZUNGEN

Du solltest zunächst den Abschnitt [Blockcontainer](./block-containers) lesen, um dich mit der Erstellung einer Containerblock-Entität vertraut zu machen.

:::

Wenn man einen Container, wie zum Beispiel eine Truhe, öffnet, sind hauptsächlich zwei Dinge erforderlich, um dessen Inhalt anzuzeigen:

- ein `Screen`, der für das Rendering des Inhalts und des Hintergrunds auf dem Bildschirm zuständig ist.
- ein `Menu`, das die Shift-Klick-Logik und die Synchronisation zwischen Server und Client handhabt.

In diesem Leitfaden erstellen wir eine Erdkiste mit einem 3x3-Container, auf den man durch einen Rechtsklick und das Öffnen einer Oberfläche zugreifen kann.

## Den Block erstellen {#creating-the-block}

Zunächst möchten wir einen Block und eine Block-Entität erstellen; weitere Informationen findest du im Leitfaden [Blockcontainer](./block-containers#creating-the-block).

@[code transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

@[code transcludeWith=:::be](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

### Das Menü öffnen {#opening-the-screen}

Wir möchten das Menü irgendwie öffnen können, daher werden wir das innerhalb der Methode `useWithoutItem` umsetzen:

@[code transcludeWith=:::use](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

### Den MenuProvider implementieren {#implementing-menuprovider}

Um die Menü-Funktionalität hinzuzufügen, müssen wir nun `MenuProvider` in der Block-Entität implementieren:

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

Die Methode `getDisplayName` gibt den Namen des Blocks zurück, der oben auf der Oberfläche angezeigt wird.

## Das Menü erstellen {#creating-the-menu}

`createMenu` erwartet, dass wir ein Menü zurückgeben, aber wir haben noch keines für unseren Block erstellt. Um dies zu tun, werden wir eine Klasse `DirtChestMenu` erstellen, welche von `AbstractContainerMenu` erbt:

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java)

Der clientseitige Konstruktor wird auf dem Client aufgerufen, wenn der Server möchte, dass dort ein Menü geöffnet wird. Es erstellt einen leeren Container, der dann automatisch mit dem tatsächlichen Container auf dem Server synchronisiert wird.

Der serverseitige Konstruktor wird auf dem Server aufgerufen, und da er den Inhalt des Containers kennt, kann er diesen direkt als Argument übergeben.

`quickMoveStack` übernimmt den Shift-Klick auf Items innerhalb des Menüs. Dieses Beispiel bildet das Verhalten von Vanilla Menüs wie Truhen und Spendern nach.

Zuerst müssen wir das Menü in einer neuen Klasse `ModMenuType` registrieren:

@[code transcludeWith=:::registerMenu](@/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java)

Wir können jetzt den Rückgabewert von `createMenu` in der Block-Entität setzen, um unser Menü zu verwenden:

@[code transcludeWith=:::providerImplemented](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

::: info

Die Methode `createMenu` wird nur auf dem Server aufgerufen, daher rufen wir den serverseitigen Konstruktor auf und übergeben `this` (die Block-Entität) als Container-Parameter.

:::

## Die Oberfläche erstellen {#creating-the-screen}

Um tatsächlich den Inhalt des Containers auf dem Client anzuzeigen, müssen wir außerdem eine Oberfläche für unser Menü erstellen.
Wir werden eine neue Klasse erstellen, welche von `AbstractContainerScreen` erbt:

@[code transcludeWith=:::screen](@/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java)

Als Hintergrund für diese Oberfläche verwenden wir einfach die Standardtextur der Werfer-Oberfläche, da unsere Erdkiste dasselbe Slot-Layout verwendet. Du könntest alternativ deine eigene Textur für `CONTAINER_TEXTURE` bereitstellen.

Da es sich hierbei um eine Oberfläche für ein Menü handelt, müssen wir es außerdem auf dem Client mit der Methode `MenuScreens#register()` registrieren:

@[code transcludeWith=:::registerScreens](@/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java)

Wenn du dein Spiel geladen hast, solltest du nun eine Erdkiste haben, die du mit einem Rechtsklick öffnen kannst, um ein Menü aufzurufen und Items darin zu verstauen.

![Menü einer Erdkiste im Spiel](/assets/develop/blocks/container_menus_0.png)
