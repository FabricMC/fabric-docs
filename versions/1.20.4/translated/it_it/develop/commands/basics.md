---
title: Creare Comandi
description: Creare comandi con parametri e azioni complesse.
authors:
  - dicedpixels
  - i509VCB
  - pyrofab
  - natanfudge
  - Juuxel
  - solidblock
  - modmuss50
  - technici4n
  - atakku
  - haykam
  - mschae23
  - treeways
  - xpple

search: false
---

# Creare Comandi

Creare comandi può permettere a uno sviluppatore di mod di aggiungere funzionalità che possono essere usate attraverso un comando. Questo tutorial ti insegnerà come registrare comandi e qual è la struttura generale dei comandi di Brigadier.

:::info
Brigadier è un parser e un dispatcher di comandi scritto da Mojang per Minecraft. È una libreria comandi basata su una gerarchia dove costruisci un albero di comandi e parametri. Brigadier è open-source: <https://github.com/Mojang/brigadier>
:::

## L'interface `Command`

`com.mojang.brigadier.Command` è un'interfaccia funzionale, che esegue del codice specifico, e lancia una `CommandSyntaxException` in determinati casi. Ha un tipo generico `S`, che definisce il tipo della _sorgente del comando_.
La sorgente del comando fornisce del contesto in cui un comando è stato eseguito. In Minecraft, la sorgente del comando è tipicamente una `ServerCommandSource` che potrebbe rappresentare un server, un blocco comandi, una connessione remota (RCON), un giocatore o un'entità.

L'unico metodo di `Command`, `run(CommandContext<S>)` prende un `CommandContext<S>` come unico parametro e restituisce un intero. Il contesto del comando contiene la tua sorgente del comando di `S` e ti permette di ottenere parametri, osservare i nodi di un comando di cui è stato effettuato il parsing e vedere l'input usato in questo comando.

Come altre interfacce funzionali, viene solitamente usata come una lambda o come un riferimento a un metodo:

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

L'intero può essere considerato il risultato del comando. Di solito valori minori o uguali a zero indicano che un comando è fallito e non farà nulla. Valori positivi indicano che il comando ha avuto successo e ha fatto qualcosa. Brigadier fornisce una costante per indicare
il successo; `Command#SINGLE_SUCCESS`.

### Cosa Può Fare la `ServerCommandSource`?

Una `ServerCommandSource` fornisce del contesto aggiuntivo dipendente dall'implementazione quando un comando viene eseguito. Questo include la possibilità di ottenere l'entità che ha eseguito il comando, il mondo in cui esso è stato eseguito o il server su cui è stato eseguito.

Puoi accedere alla sorgente del comando dal contesto del comando chiamando `getSource()` sull'istanza di `CommandContext`.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource();
    return 0;
};
```

## Registrare un Comando Basilare

I comandi sono registrati all'interno del `CommandRegistrationCallback` fornito dall'API di Fabric.

:::info
Per informazioni su come registrare i callback, vedi per favore la guida [Eventi](../events).
:::

L'evento dovrebbe essere registrato nell'initializer della tua mod.

Il callback ha tre parametri:

- `CommandDispatcher<ServerCommandSource> dispatcher` - Usato per registrare, analizzare, ed eseguire comandi. `S` è il tipo di fonte di comando che il dispatcher supporta.
- `CommandRegistryAccess registryAccess` - Fornisce un'astrazione alle registry che potrebbero essere passate ad alcuni argomenti di metodi di comandi
- `CommandManager.RegistrationEnvironment environment` - Identifica il tipo di server su cui i comandi vengono registrati.

Nell'initializer della mod, registriamo un semplice comando:

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Nel metodo `sendFeedback()` il primo parametro è il testo che viene mandato, che è un `Supplier<Text>` per evitare d'istanziare oggetti Text quando non è necessario.

Il secondo parametro determina se trasmettere il feedback agli altri operatori. In generale, se il comando deve ottenere informazioni senza effettivamente modificare il mondo, come il tempo corrente o una statistica di un giocatore, dovrebbe essere `false`. Se il comando fa qualcosa, come cambiare il tempo o modificare il punteggio di qualcuno, dovrebbe essere `true`.

Se il comando fallisce, anziché chiamare `sendFeedback()`, puoi direttamente lanciare qualsiasi eccezione e il server o il client la gestiranno in modo appropriato.

`CommandSyntaxException` generalmente viene lanciata per indicare errori di sintassi nel comando o negli argomenti. Puoi anche implementare la tua eccezione personalizzata.

Per eseguire questo comando, devi scrivere `/foo`, tutto minuscolo.

### Ambiente di Registrazione

Se vuoi, puoi anche assicurarti che un comando venga registrato solo sotto circostanze specifiche, per esempio, solo nell'ambiente dedicato:

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Requisiti dei Comandi

Immagina di avere un comando e vuoi che solo gli operatori lo possano eseguire. Questo è dove il metodo `requires()` entra in gioco. Il metodo `requires()` ha un solo argomento `Predicate<S>` che fornirà una `ServerCommandSource` con cui testare e determinare se la `CommandSource` può eseguire il comando.

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Questo comando verrà eseguito solo se la fonte del comando è un operatore di livello 2 almeno, inclusi i blocchi comandi. Altrimenti, il comando non è registrato.

Questo ha l'effetto collaterale di non mostrare il comando se si completa con tab a nessuno eccetto operatori di livello 2. Inoltre è il motivo per cui non puoi completare molti dei comandi con tab senza abilitare i comandi.

### Sotto Comandi

Per aggiungere un sotto comando, devi registrare il primo nodo letterale del comando normalmente. Per avere un sotto comando, devi aggiungere il nodo letterale successivo al nodo esistente.

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Similarmente agli argomenti, i nodi dei sotto comandi possono anch'essi essere opzionali. Nel caso seguente, sia `/subtater` che `/subtater subcommand` saranno validi.

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Comandi Lato Client

L'API di Fabric ha un `ClientCommandManager` nel package `net.fabricmc.fabric.api.client.command.v2` che può essere usato per registrare comandi lato client. Il codice dovrebbe esistere solo nel codice lato client.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Reindirizzare Comandi

I comandi reindirizzati - anche noti come alias - sono un modo di reindirizzare la funzionalità di un comando a un altro. Questo è utile quando vuoi cambiare il nome di un comando, ma vuoi comunque supportare il vecchio nome.

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Domande Frequenti (FAQ)

<br>

### Perché il Mio Codice Non Viene Compilato?

- Catturare o lanciare una `CommandSyntaxException` - `CommandSyntaxException` non è una `RuntimeException`. Se la lanci, dovresti farlo in metodi che lanciano una `CommandSyntaxException` nelle firme dei metodi, oppure dovresti catturarla.
  Brigadier gestirà le eccezioni controllate e ti inoltrerà il messaggio d'errore effettivo nel gioco.

- Problemi con i generic - Potresti avere un problema con i generic una volta ogni tanto. Se stai registrando comandi sul server (ovvero nella maggior parte dei casi), assicurati di star usando `CommandManager.literal`
  o `CommandManager.argument` anziché `LiteralArgumentBuilder.literal` o `RequiredArgumentBuilder.argument`.

- Controlla il metodo `sendFeedback()` - Potresti aver dimenticato di fornire un valore booleano come secondo argomento. Ricordati anche che, da Minecraft 1.20, il primo parametro è `Supplier<Text>` anziché `Text`.

- Un Command dovrebbe restituire un intero - Quando registri comandi, il metodo `executes()` accetta un oggetto `Command`, che è solitamente una lambda. La lambda dovrebbe restituire un intero, anziché altri tipi.

### Posso Registrare Comandi al Runtime?

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

Dopo averlo fatto, devi nuovamente inviare l'albero di comandi a ogni giocatore usando `CommandManager.sendCommandTree(ServerPlayerEntity)`.

Questo è necessario perché il client mantiene una cache locale dell'albero dei comandi che riceve durante il login (o quando i pacchetti per operatori vengono mandati) per suggerimenti locali e messaggi di errore ricchi.
:::

### Posso De-Registrare Comandi al Runtime?

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

Per tenere le cose semplici, devi usare la reflection su Brigadier e rimuovere nodi. Dopodiché, devi mandare nuovamente l'albero di comandi a ogni giocatore usando `sendCommandTree(ServerPlayerEntity)`.

Se non mandi l'albero di comandi aggiornato, il client potrebbe credere che il comando esista ancora, anche se fallirà l'esecuzione sul server.
:::
