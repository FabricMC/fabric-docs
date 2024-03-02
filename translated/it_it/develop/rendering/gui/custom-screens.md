---
title: Schermate personizzate
description: Impara come creare schermate personalizzate per la tua mod.
authors:
  - IMB11
---

# Schermate personizzate

:::info
Questa pagina si riferisce a schermate normali, non quelle gestite - queste schermate sono quelle aperte dal giocatore sul client, non quelle gesitite dal server.
:::

Le schermate sono essenzialmente le GUI con cui il giocatore interagisce, come lo schermo del titolo, la schermata di pausa ecc.

Puoi creare le tue schermate per mostrare contenuti personalizzati, un menu delle impostazioni personalizzato, e altro.

## Creare una Schermata

Per creare una schermata, devi estendere la classe `Screen` e sovrascrivere il metodo `init` - puoi opzionalmente sovrascrive anche il metodo `render` - ma assicurati di chiamate il suo metodo super altrimenti non renderizzerà il background, i widget, ecc.

Dovresti prendere nota del fatto che:

- I Widget non vengono creati nel constructor perché la schermata non è stata ancora inizializzata a quel punto - e alcune variabili, come `width` e `height`, non sono ancora disponibili o non ancora accurate.
- Il metodo `init` viene chiamata quando lo schermo viene inizializzato, e questo è il posto migliore per create i widget.
  - Aggiungi i widget usando il metodo `addDrawableChild`, che accetta qualsiasi widget disegnabile.
- Il metodo `render` viene chiamato ogni frame - puoi accedere al contesto di disegno (draw context), e alla posizione del mouse da questo metodo.

Ad esempio, possiamo creare una semplice schermata che ha un bottone e un'etichetta al di sopra.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Schermata Personalizzata 1](/assets/develop/rendering/gui/custom-1-example.png)

## Aprire la Schermata

Puoi aprire la schermata usando il metodo `setScreen` di `MinecraftClient` - puoi farlo da vari posti, come un'associazione ad un tasto, un comando, o un gestore dei pacchetti del client.

```java
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty())
);
```

## Chiudere la Schermata

Se vuoi chiudere lo schermo, semplicemente imposta la schermada su `null`:

```java
MinecraftClient.getInstance().setScreen(null);
```

Se vuoi essere sofisticato, e tornare alla schermata precedente, puoi passare la schermata corrente nel costruttore `CustomScreen` e conservalo in una variabile, per poi tornare alla schermata precedente usando il metodo `close`.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

Ora, aprendo la schermata personalizzata, puoi passare la schermata corrente come secondo argomento - quindi quando chiami `CustomScreen#close` - ritornerà alla schermata precedente.

```java
Screen currentScreen = MinecraftClient.getInstance().currentScreen;
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty(), currentScreen)
);
```
