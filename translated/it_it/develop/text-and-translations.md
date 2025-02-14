---
title: Testo e Traduzioni
description: Documentazione esaustiva riguardo alla gestione di testo e traduzioni formattate in Minecraft.
authors:
  - IMB11
  - LordEnder-Kitty
---

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

<table>
    <tbody><tr><th>Colore</th><th>Nome</th><th>Codice in Chat</th><th>Codice MOTD</th><th>Codice Hex</th></tr>
    <tr><td><ColorSwatch color="#000000" /></td><td>Nero (black)</td><td>§0</td><td>\u00A70</td><td>#000000</td></tr>
    <tr><td><ColorSwatch color="#0000AA" /></td><td>Blu Scuro (dark_blue)</td><td>§1</td><td>\u00A71</td><td>#0000AA</td></tr>
    <tr><td><ColorSwatch color="#00AA00" /></td><td>Verde Scuro (dark_green)</td><td>§2</td><td>\u00A72</td><td>#00AA00</td></tr>
    <tr><td><ColorSwatch color="#00AAAA" /></td><td>Ciano Scuro (dark_aqua)</td><td>§3</td><td>\u00A73</td><td>#00AAAA</td></tr>
    <tr><td><ColorSwatch color="#AA0000" /></td><td>Rosso Scuro (dark_red)</td><td>§4</td><td>\u00A74</td><td>#AA0000</td></tr>
    <tr><td><ColorSwatch color="#AA00AA" /></td><td>Viola Scuro (dark_purple)</td><td>§5</td><td>\u00A75</td><td>#AA00AA</td></tr>
    <tr><td><ColorSwatch color="#FFAA00" /></td><td>Oro (gold)</td><td>§6</td><td>\u00A76</td><td>#FFAA00</td></tr>
    <tr><td><ColorSwatch color="#AAAAAA"/></td><td>Grigio (gray)</td><td>§7</td><td>\u00A77</td><td>#AAAAAA</td></tr>
    <tr><td><ColorSwatch color="#555555" /></td><td>Grigio Scuro (dark_gray)</td><td>§8</td><td>\u00A78</td><td>#555555</td></tr>
    <tr><td><ColorSwatch color="#5555FF" /></td><td>Blu (blue)</td><td>§9</td><td>\u00A79</td><td>#5555FF</td></tr>
    <tr><td><ColorSwatch color="#55FF55" /></td><td>Verde (green)</td><td>§a</td><td>\u00A7a</td><td>#55FF55</td></tr>
    <tr><td><ColorSwatch color="#55FFFF" /></td><td>Ciano (aqua)</td><td>§b</td><td>\u00A7b</td><td>#55FFFF</td></tr>
    <tr><td><ColorSwatch color="#FF5555" /></td><td>Rosso (red)</td><td>§c</td><td>\u00A7c</td><td>#FF5555</td></tr>
    <tr><td><ColorSwatch color="#FF55FF" /></td><td>Viola Chiaro (light_purple)</td><td>§d</td><td>\u00A7d</td><td>#FF55FF</td></tr>
    <tr><td><ColorSwatch color="#FFFF55" /></td><td>Giallo (yellow)</td><td>§e</td><td>\u00A7e</td><td>#FFFF55</td></tr>
    <tr><td><ColorSwatch color="#FFFFFF" /></td><td>Bianco (white)</td><td>§f</td><td>\u00A7f</td><td>#FFFFFF</td></tr>
    <tr><td></td><td>Resetta</td><td>§r</td><td></td><td></td></tr>
    <tr><td></td><td><b>Grassetto</b></td><td>§l</td><td></td><td></td></tr>
    <tr><td></td><td><i>Corsivo</i></td><td>§m</td><td></td><td></td></tr>
    <tr><td></td><td><u>Sottolineato</u></td><td>§n</td><td></td><td></td></tr>
    <tr><td></td><td><s>Barrato</s></td><td>§o</td><td></td><td></td></tr>
    <tr><td></td><td>Offuscato</td><td>§k</td><td></td><td></td></tr>
</tbody></table>
