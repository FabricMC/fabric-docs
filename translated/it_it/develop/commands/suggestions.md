---
title: Suggerimenti dei Comandi
description: Impara come suggerire i valori per gli argomenti dei comandi agli utenti.
authors:
  - IMB11
---

# Suggerimenti dei Comandi {#command-suggestions}

Minecraft ha un potente sistema di suggerimento comandi che viene usato in molti posti, come nel comando `/give`. Questo sistema ti permette di suggerire valori per argomenti dei comandi all'utente, da cui possono poi selezionare - è un ottimo modo per rendere i tuoi comandi più user-friendly ed ergonomici.

## Provider di Suggerimenti {#suggestion-providers}

Un `SuggestionProvider` viene usato per creare una lista di suggerimenti che verrà mandata al client. Un provider di suggerimenti è un'interfaccia funzionale che prende un `CommandContext` e un `SuggestionBuilder` e restituisce alcune `Suggestions`. Il `SuggestionProvider` restituisce un `CompletableFuture` siccome i suggerimenti potrebbero non essere disponibili immediatamente.

## Usare i Provider di Suggerimenti {#using-suggestion-providers}

Per usare un provider di suggerimenti, devi chiamare il metodo `suggests` nel costruttore di argomenti. Questo metodo prende un `SuggestionProvider` e restituisce il costruttore di argomenti modificato con l'aggiunta del suggestion provider.

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Provider di Suggerimenti Predefiniti {#built-in-suggestion-providers}

Ci sono alcuni provider di suggerimenti predefiniti che puoi usare:

| Provider di Suggerimenti                  | Descrizione                                                             |
| ----------------------------------------- | ----------------------------------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Suggerisce tutte le entità che possono essere evocate.  |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Suggerisce tutti i suoni che possono essere riprodotti. |
| `LootCommand.SUGGESTION_PROVIDER`         | Suggerisce tutte le loot table disponibili.             |
| `SuggestionProviders.ALL_BIOMES`          | Suggerisce tutti i biomi disponibili.                   |

## Creare un Provider di Suggerimenti Personalizzato {#creating-a-custom-suggestion-provider}

Se un provider predefinito non soddisfa i tuoi requisiti, puoi creare il tuo provider di suggerimenti personalizzato. Per fare questo, devi creare una classe che implementa l'interfaccia `SuggestionProvider` e fare override del metodo `getSuggestions`.

Per questo esempio, creeremo un provider di suggerimenti che suggerisce tutti i nomi utente dei giocatori sul server.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

Per usare questo provider di suggerimenti, passeresti semplicemente una sua istanza al metodo `.suggests` nel costruttore di comandi.

Ovviamente, i provider di suggerimenti possono essere più complessi, siccome possono anche leggere il contesto dei comandi per fornire suggerimenti basati sullo stato del comando - per esempio quali argomenti sono già stati forniti.

Ciò potrebbe essere ad esempio leggere l'inventario del giocatore e suggerire oggetti, o entità che sono vicine al giocatore.
