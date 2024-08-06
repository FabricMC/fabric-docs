---
title: Benutzerdefinierte Partikel erstellen
description: Lerne, wie man benutzerdefinierte Partikel mit der Fabric API erstellt.
authors:
  - Superkat32

search: false
---

# Benutzerdefinierte Partikel erstellen

Partikel sind ein mächtiges Werkzeug. Sie können einer schönen Szene Atmosphäre oder einem spannenden Kampf gegen einen Endgegner mehr Spannung verleihen. Lasst uns einen hinzufügen!

## Einen benutzerdefinierten Partikel registrieren

Wir werden einen neuen Glitzerpartikel hinzufügen, der die Bewegung eines Partikels des Endstabs nachahmt.

Zuerst müssen wir mit deiner Mod-Id einen `ParticleType` in deiner Mod-Initialisierungsklasse registrieren.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

Der "sparkle_particle" in Kleinbuchstaben ist der JSON-Pfad für die Textur des Partikels. Du wirst später eine neue JSON-Datei mit genau diesem Namen erstellen.

## Client-seitige Registrierung

Nachdem du den Partikel im `ModInitializer` Einstiegspunkt registriert hast, musst du den Partikel auch im `ClientModInitializer` Einstiegspunkt registrieren.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

In diesem Beispiel registrieren wir unseren Partikel Client-seitig. Dann geben wir dem Partikel ein wenig Bewegung, indem wir die Factory des Endstabpartikels benutzen. Das bedeutet, dass sich unser Partikel genau wie ein Partikel eines Endstabs bewegt.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- IntelliJ's Tastaturkürzel: Strg+Alt+B
- Visual Studio Codes Hotkey: Strg+F12
:::

## Eine JSON Datei erstellen und Texturen hinzufügen

Du musst 2 Ordner in deinem `resources/assets/<mod id here>/` Ordner erstellen.

| Ordnerpfad           | Erklärung                                                                                            |
| -------------------- | ---------------------------------------------------------------------------------------------------- |
| `/textures/particle` | Der Ordner `particle` wird jegliche Texturen für alle deine Partikel enthalten.      |
| `/particles`         | Der Ordner `particles` wird jegliche JSON-Dateien für alle deine Partikel enthalten. |

Für dieses Beispiel werden wir nur eine Textur in `textures/particle` haben, die "sparkle_particle_texture.png" heißt.

Als nächstes erstelle eine neue JSON-Datei in `particles` mit demselben Namen wie der JSON-Pfad, den du bei der Registrierung deines ParticleType verwendet hast. Für dieses Beispiel müssen wir `sparkle_particle.json` erstellen. Diese Datei ist wichtig, weil sie Minecraft wissen lässt, welche Texturen unsere Partikel verwenden sollen.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
Du kannst weitere Texturen in das Array `textures` einfügen, um eine Partikelanimation zu erstellen. Der Partikel durchläuft die Texturen im Array, beginnend mit der ersten Textur.
:::

## Den neuen Partikel testen

Sobald du die JSON-Datei fertiggestellt und deine Arbeit gespeichert hast, kannst du Minecraft starten und alles testen!

Du kannst überprüfen, ob alles funktioniert hat, indem du den folgenden Befehl eingibst:

```mcfunction
/particle <mod id here>:sparkle_particle ~ ~1 ~
```

![Vorführung des Partikels](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
Mit diesem Befehl wird der Partikel im Spieler erzeugt. Du wirst möglicherweise rückwärts gehen müssen, um ihn zu sehen.
:::

Alternativ kannst du auch einen Befehlsblock verwenden, um den Partikel mit genau demselben Befehl zu erzeugen.
