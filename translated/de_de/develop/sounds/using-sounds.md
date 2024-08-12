---
title: SoundEvents abspielen
description: Lerne, wie man Sound Events abspielt.
authors:
  - JR1811
---

# SoundEvents abspielen

Minecraft hat eine große Auswahl an Sounds, aus denen du wählen kannst. Schau dir die Klasse `SoundEvents` an, um alle von Mojang bereitgestellten SoundEvent Instanzen zu sehen.

## Sounds in deinem Mod verwenden

Stelle sicher, dass du die Methode `playSound()` auf der logischen Serverseite ausführst, wenn du Sounds verwendest!

In diesem Beispiel werden die Methoden `useOnEntity()` und `useOnBlock()` für ein benutzerdefiniertes interaktives Item verwendet, um einen "platzierenden Kupferblock" und einen Plünderer-Sound abzuspielen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

Die Methode `playSound()` wird mit dem `LivingEntity` Objekt verwendet. Nur das SoundEvent, die Lautstärke und die Tonhöhe müssen angegeben werden. Es kann auch die Methode `playSound()` aus der Weltinstanz verwenden werden, um mehr Möglichkeiten bei der Parameterauswahl zu haben.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/CustomSoundItem.java)

### SoundEvent und SoundCategory

Das SoundEvent legt fest, welcher Sound abgespielt wird. Du kannst auch [deine eigenen SoundEvents registrieren](./custom), um deinen eigenen Sound einzubinden.

Minecraft hat mehrere Audioregler in den Spieleinstellungen. Das Enum `SoundCategory` wird verwendet, um zu bestimmen, mit welchem Schieberegler die Lautstärke des Sounds eingestellt wird.

### Lautstärke und Tonhöhe {#volume-and-pitch}

Der Lautstärke-Parameter kann ein wenig irreführend sein. Im Bereich von `0.0f - 1.0f` kann die aktuelle Lautstärke des Tons verändert werden. Wenn der Wert größer ist, wird die Lautstärke von `1.0f` verwendet und nur die Reichweite, in der der Ton zu hören ist, wird angepasst. Die Blockdistanz kann grob durch `Lautstärke * 16` berechnet werden.

Der Pitch-Parameter erhöht oder verringert den Wert der Tonhöhe und ändert auch die Dauer des Sounds. Im Bereich von `(0.5f - 1.0f)` wird die Tonhöhe und die Geschwindigkeit verringert, während größere Werte die Tonhöhe und die Geschwindigkeit erhöhen. Werte unter `0.5f` bleiben auf dem Wert der Tonhöhe von `0.5f`.
