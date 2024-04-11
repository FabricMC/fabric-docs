---
title: Suggerimenti di Comandi
description: Impara come suggerire i valori per gli argomenti dei comandi agli utenti.
authors:
  - IMB11
---

# Suggerimenti di Comandi

Minecraft ha un potento sistema di suggerimenti che viene usato in molti posti, come il comando `/give`. Questo sistema ti permette di suggerire valori per argomenti dei comandi all'utente, che possono poi essere selezionati - è un ottimo modo per rendere i tuoi comandi più user-friendly ed ergonomici.

## Fornitori di Suggerimenti (Suggestion Providers)

Un `SuggestionProvider` viene usato per fare una lista di suggerimenti che verrà mandata al client. Un suggestion provider è un'interfaccia funzionale che prende un `CommandContext` ed un `SuggestionBuilder` e restituisce alcune `Suggestions`. Il `SuggestionProvider` restituisce un `CompletableFuture` siccome i suggerimenti potrebbero non essere disponibili immediatamente.

## Usare i Suggestion Providers

Per usare un suggestion provider, devi chiamare il metodo `suggests` nel costruttore di argomenti. Questo metodo prende un `SuggestionProvider` e restituisce il costruttore di argomenti modificato con l'aggiunta del suggestion provider.

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Suggestion Providers Built-in

Ci sono alcuni suggestion providers già disponibili (built-in) che puoi usare:

| Suggestion Provider                       | Descrizione                                                             |
| ----------------------------------------- | ----------------------------------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Suggerisce tutte le entità che possono essere evocate.  |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Suggerisce tutti i suoni che possono essere riprodotti. |
| `LootCommand.SUGGESTION_PROVIDER`         | Suggerisce tutto le loot table che sono disponibili.    |
| `SuggestionProviders.ALL_BIOMES`          | Suggeriesce tutti i biomi disponbili.                   |

## Creare un Suggestion Provider personalizzato

Se un provider built-in non soddisfa i tuoi requisit, puoi creare il tuo suggestion provider. Per fare questo, devi creare una classe che implementa l'interfaccia `SuggestionProvider` e sovrascrive il metodo `getSuggestions`.

Per questo esempio, faremo un suggestion provider che suggerisce tutti i nomi utente dei giocatori sul server.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

Per usare questo suggestion provider, passeresti semplicemente una sua istanza nel metodo `.suggests` nel costruttore di comandi.

Ovviamente, i suggestion providers possono essere più complessi, siccome possono anche leggere il contesto dei comandi e dare suggerimenti basati sullo stato del comando - come argomenti che sono già stati forniti.

Ciò potrebbe essere nella forma di leggere l'inventario del giocatore e suggerire oggetti, o entità che sono vicine al giocatore.
