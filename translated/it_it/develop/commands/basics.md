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
---

# Creare Comandi

Creare comandi può permettere ad uno sviluppatore di mod di aggiungere funzionalità che può essere utilizzata attraverso un comando. Questo tutorial ti insegnerà come registrare comandi e qual è la struttura generale dei comandi di Brigadier.

:::info
Brigadier è un parser ed un dispatcher di comandi scritto da Mojang per Minecraft. È una libreria comandi basata su una gerarchia dove costruisci un'albero di comandi e parametri. Brigadier è open source: https\://github.com/Mojang/brigadier
:::

### L'interface `Command`

`com.mojang.brigadier.Command` è un'interface funzionale, che esegue del codice specifico, e lancia una `CommandSyntaxException` in determinati casi. Ha un tipo generico `S`, che definisce il tipo della _sorgente del comando_.
La sorgente del comando fornisce del contesto in cui un comando è stato eseguito. In Minecraft, la sorgente del comando è tipicamente una `ServerCommandSource` che potrebbe rappresentare un server, un blocco comandi, una connessione remota (RCON), un giocatore o un'entità.

L'unico metodo di `Command`, `run(CommandContext<S>)` prende un `CommandContext<S>` come unico parametro e restituisce un intero. Il contesto del comando contiene la tua sorgente del comando di `S` e ti permette di ottenere parametri, osservare i nodi di un comando di cui è stato effettuato il parsing e vedere l'input utilizzato in questo comando.

Come altre interface funzionali, viene solitamente utilizzata come una lambda o come un riferimento ad un metodo:

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

L'intero può essere considerato il risultato del comando. Tipicamente valori negativi indicano che un comando è fallito e non farà nulla. Il risultato `0` indica che il comando ha avuto successo. Valori positivi indicano che il comando ha avuto successo e ha fatto qualcosa. Brigadier fornisce una costante per indicare il successo; `Command#SINGLE_SUCCESS`.

#### Cosa Può Fare la `ServerCommandSource`?

Una `ServerCommandSource` fornisce del contesto aggiuntivo dipendente dall'implementazione quando un comando viene eseguito. Questo include la possibilità di ottenere l'entità che ha eseguito il comando, il mondo in cui esso è stato eseguito o il server su cui è stato eseguito.

Puoi accedere alla sorgente del comando dal contesto del comando chiamando `getSource()` sull'istanza di `CommandContext`.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource(); 
    return 0;
};
```

### Registrare un Comando Basilare

I comandi sono registrati all'interno del `CommandRegistrationCallback` fornito dall'API di Fabric.

:::info
Per informazioni su come registrare i callback, vedi per favore la guida [Eventi](../events.md).
:::

L'evento dovrebbe essere registrato nel tuo mod initializer.

La callback ha tre parametri:

- `CommandDispatcher<ServerCommandSource> dispatcher` - Usato per registrare, analizzare, ed eseguire comandi. `S` è il tipo di fonte di comando (command source) che il dispatcher supporta.
- `CommandRegistryAccess registryAccess` - Fornice un'astrazione per i registri che potrebbero essere passati ad alcuni argomenti di metodi di comandi
- `CommandManager.RegistrationEnvironment environment` - Identifica il tipo di server su cui i comandi verrano registrati.

Nel mod initializer, registriamo un semplice comando:

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Nel metodo `sendFeedback()` il primo parametro è il testo che viene mandato, che è un `Supplier<Text>` per evitare di instanziare oggetti Text quando non è necessario.

Il secondo parametro determina se fare un broadcast del feedback agli altri operatori. Generalmente, se il comando deve ottenere informazioni senza modificare il mondo, come l'ottenere il tempo di gioco, o una statistica di un giocatore, dovrebbe essere `false`. Se il comando fa qualcosa, come cambiare il tempo o modica il punteggio di qualcuno, dovrebbe essere `true`.

Se il comando fallisce, anziché chiamare `sendFeedback()`, puoi direttamente fare un throw di un exception e il server o il client la gestiranno in modo appropriato.

`CommandSyntaxException` generalmente viene restituita per indicare errori di sintassi nel comando o negli argomenti. Puoi anche implementare la tua exception personalizzata.

Per eseguire questo comando, devi scrivere `/foo`, che è case-sensitive.

#### Ambiente di Registrazione

Se vuoi, puoi anche assicuratrti che un comando venga registrato sotto circostanze specifiche, per esempio solo nell'ambiente del server dedicato:

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

#### Requisiti dei Comandi

Diciamo che hai un comando, e vuoi sia disponibile solo agli operatori. Questo è dove il metodo `requires()` entra in gioco. Il metodo `requires()` ha un solo argomento di un `Predicate<S>` che fornirà un `ServerCommandSource` da testare e determinare se il `CommandSource` può eseguire il comando.

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Questo comando verrà eseguito solo se la sua fonte è un operatore di livello 2 come minimo, command block inclusi. Altrimenti, il comando non è registrato.

Questo ha l'effetto collaterale di non far vedere il comando nella completazione tab a nessuno che non sia un operatore di livello 2. Inoltre è il motivo per cui non puoi tab-completare molti dei comandi senza abilitare i trucchi.

#### Sotto Comandi

Per aggiungere un sotto comando, devi registrare prima un nodo literal del comando normalmente. Per avere un sotto coamndo, devi aggiungere il prossimo nodo literal al nodo esistente.

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Similarmente agli argomenti, i nodi dei sottocomandi posso anch'essi essere opzionali. Nel caso seguente, sia `/subtater`
che `/subtater subcommand` saranno validi.

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Comandi lato Client

Fabric API ha una classe `ClientCommandManager` nel package `net.fabricmc.fabric.api.client.command.v2` che può essere usata per registrare comandi lato client. Il codice dovrebbe esistere solo nel codice lato client.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

### Reindirizzare Comandi

Reindirizzazio di Comandi - conosciuti anche come alias - sono un modo per reindirizzare la funzionalità di un comando ad un altro. Questo è utile quando vuoi cambiare il nome di un comando, ma vuoi comunque supportare il vecchio nome.

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

### FAQ (Domande Frequenti)

<br>

###### Perché il mio codice non viene compilato?

- Fare un catch o un throw di `CommandSyntaxException` - `CommandSyntaxException` non è una `RuntimeException`. Se fai un throw di questa exception, dovresti farlo in metodi che fanno un throw di `CommandSyntaxException`, oppure dovresti fare un catch della stessa.
  Brigadier gestierà le exception controllate e manderà il messaggio d'errore effettivo nel gioco per te.

- Problemi con i generic - Potresti avere un problem con i generics una volta ogni tanto. Se stai registrando comandi sul server (ovvero nella maggior parte dei casi), assicurati di star usando `CommandManager.literal`
  o `CommandManager.argument` anzichè `LiteralArgumentBuilder.literal` o `RequiredArgumentBuilder.argument`.

- Controlla il metodo `sendFeedback()` - Potresti aver dimenticato di fornire un valore boolean come secondo argomento. Ricordanti anche che, da minecraft 1.20, il primo parametro è `Supplier<Text>` anziché `Text`.

- Un comando dovrebbe restituire un integer - Quando registri un comando, il metodo `executes()` accetta un oggetto `Command` che è solitamente una lambda. La lambda dovrebbe restituire un integer, anziché altri tipi.

###### Posso registrare comandi al runtime?

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

Dopo averlo fatto, devi mandare l'albero di comandi ad ogni giocatore nuovamente usando `CommandManager.sendCommandTree(ServerPlayerEntity)`.

Questo è necessario perché il client tiene delle cache locali dell'albero di comando che riceve durante il login (o quando i pacchetti per operatori vengono mandati) per messaggi di errori ricchi e completi.
:::

###### Posso de-registrare comandi al runtime?

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

Per tenere le cose semplici, devi usare la reflection su Brigadier e rimuove nodi. Dopodiché, devi mandare l'albero di comandi ad ogni player di nuovo usando `sendCommandTree(ServerPlayerEntity)`.

Se non mandi l'albero di comandi aggiornato, il client potrebbe pensare che il comando esiste ancora, anche se il server fallisce l'esecuzione.
:::
