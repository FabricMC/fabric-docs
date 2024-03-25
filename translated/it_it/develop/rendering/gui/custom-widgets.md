---
title: Widget personalizzati
description: Impara come creare widget peraonalizzati per le tua schermate.
authors:
  - IMB11
---

# Widget personalizzati

Gli Widget sono essenzialmente componenti di rendering containerizzate, e i giocatori possono interagirci attraverso vari eventi come click del mouse, pressione di tasti, e altro.

## Creare un Widget

Ci sono varie strade per creare una classe widget, tipo estendere `ClickableWidget`. Questa classe fornisce un sacco di utilities, come gestire la larghezza, altezza, posizione, e gestire gli eventi - implementa le interfacce `Drawable`, `Element`, `Narratable`, e `Selectable`:

- `Drawable` - per il rendering - Richiesto per registrate il widget sulla schermata usando il metodo `addDrawableChild`.
- `Element` - per eventi - Necessario se vuoi gestire gli eventi come click del mouse, pressione tasti, e altro.
- `Narratable` - per accessibilità - Necessario per fare il tuo widget accessibile ai lettori di schermi e altri strumenti per l'accessibilità.
- `Selectable` - per la selezione - Necessario se vuoi rendere il tuo widget selezionabile usando il testo <kbd>Tab</kbd> - anche questo aiuta per l'accessibilità.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Aggiungere il Widget alla Schermata

Come tutti i widget, devi aggiungerlo alla schermata usando il metodo `addDrawableChild`, che è fornito dalla classe `Screen`. Assicurareti di farlo nel metodo `init`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Widget personalizzato sullo schermo.](/assets/develop/rendering/gui/custom-widget-example.png)

## Eventi Widget

Puoi gestire eventi come click del mouse, pressione di tasti, sovrascrivendo i metodi `onMouseClicked`, `onMouseReleased`, `onKeyPressed`, e altri.

Per esempio, puoi far cambiare colore al widget quando il mouse ci passa sopra (hovering) usando il metodo `isHovered()` fornito dalla classe `ClickableWidget`:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Esempio Evento Hovering](/assets/develop/rendering/gui/custom-widget-events.webp)
