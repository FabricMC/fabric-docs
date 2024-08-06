---
title: Widget Personalizzati
description: Impara come creare widget personalizzati per la tua schermata.
authors:
  - IMB11

search: false
---

# Widget Personalizzati

I Widget sono essenzialmente componenti di rendering containerizzate che possono essere aggiunti a una schermata, e i giocatori possono interagirci attraverso vari eventi come clic del mouse, pressione di tasti, e altro.

## Creare un Widget

Si possono seguire varie strade per creare una classe widget, come estendere `ClickableWidget`. Questa classe fornisce un sacco di utilità, come la gestione di larghezza, altezza, posizione, e quella degli eventi - implementa le interfacce `Drawable`, `Element`, `Narratable`, e `Selectable`:

- `Drawable` - per il rendering - Necessario per registrare il widget alla schermata usando il metodo `addDrawableChild`.
- `Element` - per eventi - Necessario se vuoi gestire gli eventi come click del mouse, pressione di tasti, e altro.
- `Narratable` - per l'accessibilità - Necessario per rendere il tuo widget accessibile a lettori di schermi e ad altri strumenti per l'accessibilità.
- `Selectable` - per la selezione - Necessario se vuoi rendere il tuo widget selezionabile usando il tasto <kbd>Tab</kbd> - anche questo aiuta per l'accessibilità.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## Aggiungere il Widget alla Schermata

Come tutti i widget, devi aggiungerlo alla schermata usando il metodo `addDrawableChild`, che è fornito dalla classe `Screen`. Assicurati di farlo nel metodo `init`.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Widget personalizzato sullo schermo](/assets/develop/rendering/gui/custom-widget-example.png)

## Eventi di Widget

Puoi gestire eventi come click del mouse, pressione di tasti, facendo override dei metodi `onMouseClicked`, `onMouseReleased`, `onKeyPressed`, e altri.

Per esempio, puoi far cambiare colore al widget quando il mouse ci passa sopra usando il metodo `isHovered()` fornito dalla classe `ClickableWidget`:

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![Esempio Evento Hovering](/assets/develop/rendering/gui/custom-widget-events.webp)
