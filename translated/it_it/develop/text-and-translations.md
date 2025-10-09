---
title: Testo e Traduzioni
description: Documentazione esaustiva riguardo alla gestione di testo e traduzioni formattate in Minecraft.
authors:
  - IMB11
  - LordEnder-Kitty
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Ogni volta che Minecraft mostra testo nel gioco, è con tutta probabilità definito da un oggetto `Text`.
Questo tipo personalizzato è preferito ad una `String` per permettere formattazione più avanzata, che comprende colori, grassetto, offuscamento, ed eventi ai clic. Permettono inoltre accesso più semplice al sistema di traduzione, rendendo semplice la traduzione di qualsiasi elemento dell'interfaccia.

Se hai mai lavorato con datapack o con funzioni prima d'ora, potresti notare similarità con il formato testo json usato per i displayName, i libri, e i cartelli tra le altre cose. Come probabilmente riuscirai ad indovinare, quella è solo una rappresentazione json di un oggetto `Text`, e può essere convertita tramite `Text.Serializer`.

Quando si crea una mod, si preferisce generalmente costruire i tuoi oggetti `Text` direttamente nel codice, sfruttando ove possibile le traduzioni.

## Testo Letterale {#text-literals}

Il modo più semplice di creare un oggetto `Text` è creare un testo letterale. Questa è proprio solo una stringa che verrà visualizzata com'è, senza alcuna formattazione predefinita.

Questi sono creati tramite i metodi `Text.of` o `Text.literal`, che agiscono in modi un po' diversi. `Text.of` accetta null come input, e restituirà un'istanza di `Text`. Viceversa, `Text.literal` deve non ricevere come input null, ma restituisce un `MutableText`, che è una sotto-classe di `Text` facilmente stilizzata e concatenata. Ne parleremo di più dopo.

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## Testo Traducibile {#translatable-text}

Se volessi fornire traduzioni multiple della stessa stringa di testo, puoi usare il metodo `Text.translatable` per fare riferimento ad una chiave di traduzione in qualsiasi file lingua. Se la chiave di traduzione non esistesse, verrebbe convertita in testo letterale.

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

Il file di lingua, `en_us.json`, ha il seguente aspetto:

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

Se volessi usare variabili nella traduzione, in maniera simile a come i messaggi di morte ti permettodo di usare i giocatori e gli oggetti coinvolti nella traduzione, puoi aggiungere queste variabili come parametri. Puoi aggiungerne quanti ne vuoi.

```java
Text translatable = Text.translatable("my_mod.text.hello", player.getDisplayName());
```

Puoi fare riferimento a queste variabili nella traduzione così:

```json
{
  "my_mod.text.hello": "%1$s said hello!"
}
```

Nel gioco, %1\$s sarà sostituito dal nome del giocatore a cui hai fatto riferimento nel codice. Usare `player.getDisplayName()` farà in modo che certe informazioni aggiuntive sull'entità appaiano in un tooltip mentre si passa il mouse sopra al nome nel messaggio in chat, e ciò in contrasto con `player.getName()`, che mostrerà comunque il nome, ma non i dettagli aggiuntivi. Qualcosa di simile si può fare con gli itemStack, usando `stack.toHoverableText()`.

Per quanto riguarda il significato di %1\$s, ti basti sapere che il numero corrisponde a quale variabile provi ad usare. Immagina di usare tre variabili.

```java
Text translatable = Text.translatable("my_mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

Se vuoi fare riferimento a ciò che, nel nostro caso, è l'attaccante, useresti %2\$s perché è la seconda variabile che abbiamo passato. Allo stesso modo, %3\$s fa riferimento all'itemStack. Una traduzione con questo numero di parametri aggiuntivi potrebbe avere questo aspetto:

```json
{
  "my_mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## Serializzare Testo {#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

Come già accennato prima, puoi serializzare testo a JSON con il codec di testo. Per maggiori informazioni, vedi la pagina sui [Codec](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

Questo produce JSON che può essere usato in datapack, comandi e altri posti che accettano il formato JSON di testo invece che il formato letterale o traducibile.

## Deserializzare Testo {#deserializing-text}

Inoltre, per deserializzare un oggetto testo da JSON a un oggetto della classe `Text`, di nuovo, usa il codec.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Formattare Testo {#formatting-text}

Forse sei familiare con gli standard di formattazione di Minecraft:

Puoi applicare questi stili di formattazione usando l'enum `Formatting` sulla classe `MutableText`:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

|              Colore             | Nome                             | Codice in Chat | Codice MOTD | Codice Hex |
| :-----------------------------: | -------------------------------- | :------------: | :---------: | :--------: |
| <ColorSwatch color="#000000" /> | Nero<br />`black`                |      `§0`      |  `\u00A70` |  `#000000` |
| <ColorSwatch color="#0000AA" /> | Blu Scuro<br />`dark_blue`       |      `§1`      |  `\u00A71` |  `#0000AA` |
| <ColorSwatch color="#00AA00" /> | Verde Scuro<br />`dark_green`    |      `§2`      |  `\u00A72` |  `#00AA00` |
| <ColorSwatch color="#00AAAA" /> | Ciano Scuro<br />`dark_aqua`     |      `§3`      |  `\u00A73` |  `#00AAAA` |
| <ColorSwatch color="#AA0000" /> | Rosso Scuro<br />`dark_red`      |      `§4`      |  `\u00A74` |  `#AA0000` |
| <ColorSwatch color="#AA00AA" /> | Viola Scuro<br />`dark_purple`   |      `§5`      |  `\u00A75` |  `#AA00AA` |
| <ColorSwatch color="#FFAA00" /> | Oro<br />`gold`                  |      `§6`      |  `\u00A76` |  `#FFAA00` |
| <ColorSwatch color="#AAAAAA" /> | Grigio<br />`gray`               |      `§7`      |  `\u00A77` |  `#AAAAAA` |
| <ColorSwatch color="#555555" /> | Grigio Scuro<br />`dark_gray`    |      `§8`      |  `\u00A78` |  `#555555` |
| <ColorSwatch color="#5555FF" /> | Blu<br />`blue`                  |      `§9`      |  `\u00A79` |  `#5555FF` |
| <ColorSwatch color="#55FF55" /> | Verde<br />`green`               |      `§a`      |  `\u00A7a` |  `#55FF55` |
| <ColorSwatch color="#55FFFF" /> | Ciano<br />`aqua`                |      `§b`      |  `\u00A7b` |  `#55FFFF` |
| <ColorSwatch color="#FF5555" /> | Rosso<br />`red`                 |      `§c`      |  `\u00A7c` |  `#FF5555` |
| <ColorSwatch color="#FF55FF" /> | Viola Chiaro<br />`light_purple` |      `§d`      |  `\u00A7d` |  `#FF55FF` |
| <ColorSwatch color="#FFFF55" /> | Giallo<br />`yellow`             |      `§e`      |  `\u00A7e` |  `#FFFF55` |
| <ColorSwatch color="#FFFFFF" /> | Bianco<br />`white`              |      `§f`      |  `\u00A7f` |  `#FFFFFF` |
|                                 | Resetta                          |      `§r`      |             |            |
|                                 | **Grassetto**                    |      `§l`      |             |            |
|                                 | ~~Barrato~~                      |      `§m`      |             |            |
|                                 | <u>Sottolineato</u>              |      `§n`      |             |            |
|                                 | _Corsivo_                        |      `§o`      |             |            |
|                                 | Offuscato                        |      `§k`      |             |            |
